package anyeight.serviceImpl.strategy;

import anyeight.dao.StockMapper;
import anyeight.model.AdjClose;
import anyeight.util.MathHelper;
import anyeight.util.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by 啊 on 2017/5/14.
 */
@Service("meanReverting")
public class MeanReverting extends QuantitativeServiceImpl {
    @Autowired
    StockMapper stockMapper;
    @Autowired
    Strategy strategy;

    @Override
    protected StrategyModel getStrategyModel(Date before, int formative, List<String> blocks, StrategyType strategyType) {
        Map<String, Double> yield = new HashMap<>();
        Map<String ,Double>adjClose=new HashMap<>();
        Map<String, Double> canBuyList = new HashMap<>();

        List<AdjClose>adjClosePOs1=new ArrayList<>();
        Date beforeDate=stockMapper.getExCalendar("",MathHelper.utilToSql(before),formative-1);
        if(beforeDate==null)
            return null;
        adjClosePOs1=stockMapper.getAdjClose(blocks,MathHelper.utilToSql(beforeDate),MathHelper.utilToSql(before));

        for(int i=0;i<adjClosePOs1.size();i++){
            Boolean isCorrect=true;
            AdjClose adjClosePO=adjClosePOs1.get(i);
            ArrayList<Double>tempData=new ArrayList<>();
            if(adjClosePO.getMap().size()!=0) {
                Iterator<Map.Entry<Date, Double>> iterator1 = adjClosePO.getMap().entrySet().iterator();
                Map.Entry<Date, Double> tail1 = null;
                while (iterator1.hasNext()) {
                    tail1 = iterator1.next();
                    if (tail1.getValue() == null) {
                        isCorrect = false;
                        break;
                    }
                    tempData.add(tail1.getValue());
                    // System.out.println(tail1.getValue());
                }
                if (isCorrect)
                    adjClose.put(adjClosePO.getID(), MathHelper.getAverage(tempData, tempData.size()));
            }
        }
        List<AdjClose>adjClosePOs=stockMapper.getAdjClose(blocks,MathHelper.utilToSql(before),MathHelper.utilToSql(before));
        Map<String,Double>openPrice=strategy.getOpenPrice(before);
        for(int i=0;i<adjClosePOs.size();i++){
            AdjClose adjClosePO=adjClosePOs.get(i);
            String code=adjClosePO.getID();
            if(!adjClose.containsKey(code))
                continue;
            if(!adjClosePO.getMap().containsKey(before))                    //我觉得不需要但好像还是需要。。
                continue;
            double eachAdjClose=adjClose.get(code);
            double actualAdjClose=adjClosePO.getMap().get(before);
            if(openPrice.containsKey(code)) {
                yield.put(code, (eachAdjClose - actualAdjClose) / actualAdjClose);
                canBuyList.put(code,openPrice.get(code));
            }
        }
        StrategyModel strategyModel = new StrategyModel(canBuyList, yield);
        return strategyModel;
    }
}

