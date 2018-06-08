package anyeight.serviceImpl.strategy;

import anyeight.dao.StockMapper;
import anyeight.service.QuantitativeService;
import anyeight.service.StockService;
import anyeight.util.LineType;
import anyeight.util.MathHelper;
import anyeight.util.StrategyType;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 啊 on 2017/5/11.
 */
@Service
public class QuantitativeServiceImpl implements QuantitativeService {
    @Autowired
    StockMapper stockMapper;
    @Autowired
    StockService stockService;
    @Autowired
    Strategy strategy;





    public QuantitativeVO getAll(Date startTime, Date endTime, int formative, int hold,  List<SelectVO>selectVOs) {
        ArrayList<String>stockList=new ArrayList<String>();
        Map<String,String> map=stockService.getIDList();
        Iterator<Map.Entry<String,String>> iterator = map.entrySet().iterator();
        Map.Entry<String,String> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
            stockList.add(tail.getValue());
        }
        QuantitativeVO quantitativeVO=getAmongStocks(startTime,endTime,formative,hold,"sh000001",stockList,selectVOs,1);
        return quantitativeVO;
    }


    public QuantitativeVO getAmongBlock(Date startTime, Date endTime, int formative, int hold, String blockName, List<SelectVO>selectVOs) {
        switch (blockName){          
            case"":blockName="sh000001";break;
            case "主板":blockName="sh000300";break;
            case"中小板": blockName="sz399005";break;
            case"创业板":blockName="sz399006";break;
            default:{
                QuantitativeVO quantitativeVO=new QuantitativeVO(null,null,-1,-1,-1,-1,-1,-1,null,null);
                return quantitativeVO;
            }
        }
        List<String>stockList=stockMapper.getStocksByPlate(blockName);
        QuantitativeVO quantitativeVO=getAmongStocks(startTime,endTime,formative,hold,blockName,stockList,selectVOs,2);
        return quantitativeVO;
    }


    public QuantitativeVO getAmongStocks(Date startTime, Date endTime, int formative, int hold, String blockName, List<String> blocks, List<SelectVO>selectVOs,int type) {
       if(type==3){
           Map<String,String> map=stockService.getIDList();
           List<String>list=new ArrayList<>();
           for(int i=0;i<blocks.size();i++){
               list.add(map.get(blocks.get(i)));
           }
           blocks=list;
       }
        Map temp=new HashMap();
        List temp2=new ArrayList();
        QuantitativeVO quantitativeVO=new QuantitativeVO(temp,temp,-1,-1,-1,-1,-1,-1,temp,temp2);
        List<List<String>> totalBuyList=new ArrayList<>();
        Date before=null;
        Date exCalendar=stockMapper.getExCalendar("", MathHelper.utilToSql(startTime),1);                       //将before转化为实际开始日期
        if(exCalendar!=null) {
            before= stockMapper.getAfterCalendar("", MathHelper.utilToSql(exCalendar), 1);
            if(before==null)
                return quantitativeVO;
        }
        else
            return quantitativeVO;
        Date actualStart=(Date)before.clone();
        Date afterAHoldTime=null;
        while(!before.after(endTime)) {
            StrategyModel strategyModel = getStrategyModel(before, formative, blocks, selectVOs.get(0).getStrategyType());
            List<String>buyCodeList= strategy.selectStock(strategyModel.getYield(),selectVOs.get(0));
            Map canBuyList=strategyModel.getCanBuyList();
            for(int i=1;i<selectVOs.size();i++) {
                strategyModel = getStrategyModel(before, formative, blocks, selectVOs.get(i).getStrategyType());
                List<String>secondBuyCode= strategy.selectStock(strategyModel.getYield(),selectVOs.get(i));
                buyCodeList.retainAll(secondBuyCode);                                                           //几个策略选股求交集
            }
            if(buyCodeList.size()==0){
                return quantitativeVO;
            }
            totalBuyList.add(buyCodeList);
            afterAHoldTime=stockMapper.getAfterCalendar("", MathHelper.utilToSql(before),hold-1);
            if(afterAHoldTime==null)
                return quantitativeVO;
            if(afterAHoldTime.after(endTime)){
                afterAHoldTime=stockMapper.getAfterCalendar("",MathHelper.utilToSql(endTime),1);                      //用户输入的持有期过大
                 afterAHoldTime=stockMapper.getExCalendar("",MathHelper.utilToSql(afterAHoldTime),1);
                if(afterAHoldTime.after(endTime))
                    afterAHoldTime=stockMapper.getExCalendar("",MathHelper.utilToSql(afterAHoldTime),1);
            }
            strategy.setEndHoldDate(afterAHoldTime);
            strategy.setLine(before,afterAHoldTime,buyCodeList, LineType.STRATEGY_LINE,canBuyList);
            if(type==3) {
                strategy.setLine(before, afterAHoldTime, blocks, LineType.STANDARD_LINE,canBuyList);
            }
            before=stockMapper.getAfterCalendar("",MathHelper.utilToSql(afterAHoldTime),1);
        }

        Boolean canAdd=true;
        if(type==1||type==2){
            canAdd=strategy.setStandardLine(actualStart,endTime,blockName);
        }
               //爬不到基准数据
        if(!canAdd) {
            strategy.release();
            return null;
        }
        QuantitativeVO quantitativeVO2=strategy.getAll(startTime,endTime,totalBuyList);
        quantitativeVO2.setEvaluate(pairedSymbolicTest(quantitativeVO2));
        strategy.release();
        return quantitativeVO2;
    }


    public List<String> getBlockNameList() {
        List<String> result=stockMapper.getBlockNameList();
        if(result!=null)
            return result;
        else{
            List list=new ArrayList();
            return list;
        }
    }


    public List<String> getBlockInClass(String className) {
        List<String> result=stockMapper.getStocksNameByPlate(className);
        if(result!=null)
            return result;
        else{
            List list=new ArrayList();
            return list;
        }
    }


    public Boolean saveStrategy(List<SelectVO> selectVOs, String strategyName,String user_id) {
        return null;
    }


    public String pairedSymbolicTest(QuantitativeVO quantitativeVO) {
        List<Double> strategy=quantitativeVO.getStrategyEarningList();
        List<Double> standard=quantitativeVO.getStandardEarningList();
        int strategyWin=0;
        int strategyLoss=0;
        double c;
        for(int i=0;i<strategy.size();i++){
            if(strategy.get(i)>standard.get(i))
                strategyWin++;
            else if(strategy.get(i)<standard.get(i))
                strategyLoss++;
        }
        if(strategyWin>strategyLoss)
            c=0.5;
        else
            c=-0.5;
        double u=(strategyWin-strategyLoss+c)/Math.pow(strategyLoss+strategyWin,0.5);
        if(u<=1.96&&u>=-1.96)
            return "此策略价值型可能不大";
        else if(u>1.96)
            return "此策略有一定效果";
        else
            return "此策略可能有副作用，请谨慎使用";
    }

    protected  StrategyModel getStrategyModel(Date before,int formative, List<String> blocks, StrategyType strategyType){
        return null;
    }
}
