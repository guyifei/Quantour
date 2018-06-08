package anyeight.serviceImpl.strategy;

import anyeight.dao.StockMapper;
import anyeight.model.Stock;

import anyeight.util.MathHelper;
import anyeight.util.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 啊 on 2017/6/5.
 */
@Service("customStrategy")
public class CustomStrategy extends QuantitativeServiceImpl{
    @Autowired
    Strategy strategy;
    @Autowired
    StockMapper stockMapper;
    List<Stock>stocks;
    protected StrategyModel getStrategyModel(Date before, int formative, List<String> blocks,StrategyType strategyType) {
        Map<String,Double> canBuyList=new HashMap<>();
        Map<String, Double> yield = new HashMap<>(blocks.size());
        Date formativeDate= stockMapper.getExCalendar("", MathHelper.utilToSql(before),formative-1);
        if(formativeDate==null)
            return null;                                                 //在该持有期找不到数据
        if(stocks==null)
            stocks=stockMapper.getStockList(MathHelper.utilToSql(formativeDate),MathHelper.utilToSql(before),blocks);
        Map<String,Double>openPrice=strategy.getOpenPrice(before);
        Map<String,Double>resultPerStock=new HashMap<>();
        Map<String,Integer>timesPerStock=new HashMap<>();
        for (int i = 0; i < stocks.size(); i++) {
            String code=stocks.get(i).getCode();
            double putValue=0;
            if(strategyType.equals(StrategyType.VOLUMN))
                putValue=stocks.get(i).getVolome();
            else if(strategyType.equals(StrategyType.HIGH))
                putValue=stocks.get(i).getHigh();
            else if(strategyType.equals(StrategyType.CLOSE))
                putValue=stocks.get(i).getClose();
            else if(strategyType.equals(StrategyType.ADJ_CLOSE))
                putValue=stocks.get(i).getAdjClose();
            else if(strategyType.equals(StrategyType.OPEN))
                putValue=stocks.get(i).getOpen();
            if(openPrice.containsKey(code)) {
                if(resultPerStock.containsKey(code)){
                    resultPerStock.replace(code,resultPerStock.get(code)+putValue);
                    timesPerStock.replace(code,timesPerStock.get(code)+1);
                }
                else{
                    resultPerStock.put(code,putValue);
                    timesPerStock.put(code,1);
                    canBuyList.put(code,openPrice.get(code));
                }
            }
        }
        Iterator<Map.Entry<String,Double>> iterator1 = resultPerStock.entrySet().iterator();
        Map.Entry<String,Double> tail1 = null;
        while (iterator1.hasNext()) {
            tail1 = iterator1.next();
            yield.put(tail1.getKey(),tail1.getValue()/timesPerStock.get(tail1.getKey()));
        }
        StrategyModel strategyModel=new StrategyModel(canBuyList,yield);
        return strategyModel;
    }
}
