package anyeight.serviceImpl;

import anyeight.dao.StockMapper;
import anyeight.service.FindProperSectionService;
import anyeight.service.QuantitativeService;
import anyeight.util.MathHelper;
import anyeight.util.SelectType;
import anyeight.util.StrategyType;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.SearchProperVO;
import anyeight.vo.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 啊 on 2017/5/11.
 */
@Service
public class FindProperSectionServiceImpl implements FindProperSectionService {
    @Autowired
    private StockMapper stockMapper;
    QuantitativeService quantitativeService;
    @Autowired
    @Qualifier("momentumStrategy")
    QuantitativeService quantitativeService1;
    @Autowired
    @Qualifier("meanReverting")
    QuantitativeService quantitativeService2;
    @Autowired
    @Qualifier("customStrategy")
    QuantitativeService quantitativeService3;


    public SearchProperVO findPropperHold(Date startTime, Date endTime, int formative,List<SelectVO>selectVOs) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(startTime);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endTime);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days=day2-day1;
        int i=days/6;
        int add=i;
        Map<Integer,Double> excessEarning=new TreeMap<>();
        Map<Integer,Double> strategyWin=new TreeMap<>();
        Date temp= null;
        temp = stockMapper.getAfterCalendar("", MathHelper.utilToSql(startTime),i);              //持有期不能少于5天
        if(temp==null){
            SearchProperVO searchProperVO=new SearchProperVO(excessEarning,strategyWin);
            return searchProperVO;
        }


        while(!temp.after(endTime)) {

                setQuantitativeService(selectVOs.get(0));
                //progressOfAll.setProgress(((double) temp.getTimeInMillis() - a) / total);

                // System.out.println("aaa");
                //isProper=true;
                QuantitativeVO quantitativeVO = quantitativeService.getAll(startTime, endTime, formative, i, selectVOs);
                if (quantitativeVO.getAlpha() == -1 && quantitativeVO.getBeta() == -1 && quantitativeVO.getStandardAnnualizedReturn() == -1) {
                    SearchProperVO searchProperVO = new SearchProperVO(excessEarning, strategyWin);
                    return searchProperVO;
                }
                //isProper=false;
                //System.out.println("bbb");
                excessEarning.put(i, getChaoE(quantitativeVO));
                strategyWin.put(i, getShenglv(quantitativeVO));
                i += add;
                temp = stockMapper.getAfterCalendar("", MathHelper.utilToSql(startTime), i);
        }
        SearchProperVO searchProperVO=new SearchProperVO(excessEarning,strategyWin);
        return searchProperVO;
    }

    public SearchProperVO findProperFormative(Date startTime, Date endTime, int hold, List<SelectVO>selectVOs) {
        setQuantitativeService(selectVOs.get(0));
        Map<Integer,Double>excessEarning=new TreeMap<>();
        Map<Integer,Double> strategyWin=new TreeMap<>();
        for(int i=5;i<=20;i+=5) {
            //isProper=true;
            QuantitativeVO quantitativeVO=quantitativeService.getAll(MathHelper.utilToSql(startTime),MathHelper.utilToSql(endTime),i,hold,selectVOs);
            if(quantitativeVO.getAlpha()==-1&&quantitativeVO.getBeta()==-1&&quantitativeVO.getStandardAnnualizedReturn()==-1){
                SearchProperVO searchProperVO=new SearchProperVO(excessEarning,strategyWin);
                return searchProperVO;
            }
           // isProper=false;
            excessEarning.put(i, getChaoE(quantitativeVO));
            strategyWin.put(i,getShenglv(quantitativeVO));
        }
        SearchProperVO searchProperVO=new SearchProperVO(excessEarning,strategyWin);
        return searchProperVO;
    }

    private double getChaoE(QuantitativeVO quantitativeVO){
        double result=getReturn(quantitativeVO.getStrategyEarningList())-getReturn(quantitativeVO.getStandardEarningList());
        return result;
    }

    private double getReturn(List<Double> list){
        int size=list.size();
        if(size==0)
            return 0;
        return list.get(size-1);
    }

    private double getShenglv(QuantitativeVO quantitativeVO){
        int zheng=0;
        Map map=quantitativeVO.getYieldDistributionMap();
        int size=0;
        Iterator<Map.Entry<Double,Integer>> iterator1 = map.entrySet().iterator();
        Map.Entry<Double,Integer> tail1 = null;
        while (iterator1.hasNext()) {
            tail1 = iterator1.next();
            if(tail1.getKey()>0) {
                zheng += tail1.getValue();
            }
            size+=tail1.getValue();
        }
        double result=1.0*zheng/size;
        //System.out.println("result"+result);
        return result;
    }
    private void setQuantitativeService(SelectVO selectVO){
        StrategyType strategyType=selectVO.getStrategyType();
        if(strategyType.equals(StrategyType.MOMENTUM))
            quantitativeService=quantitativeService1;
        else if(strategyType.equals(StrategyType.MEAN_REVERTING))
            quantitativeService=quantitativeService2;
        else
            quantitativeService=quantitativeService3;
    }
}
