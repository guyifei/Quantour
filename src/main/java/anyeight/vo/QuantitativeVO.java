package anyeight.vo;

import java.util.*;

/**
 * Created by 段梦洋 on 2017/3/21.
 */
public class QuantitativeVO {
    /***
    private Map<Date,Double> strategyEarning;                      //每天的策略收益率
    private Map<Date,Double> standardEarning;                       //每天的基准收益率
     ***/
    private double annualizedReturn;                                   //年收益率
    private double standardAnnualizedReturn;                           //年基准收益率
    private double maximumDrawdown;                                      //最大回撤
    private double Beta;
    private double Alpha;
    private double sharpeRatie;                                           //夏普比率
    private Map<Double,Integer>yieldDistributionMap;                     //每百分之几的收益率有几个股票（直方图要用）
    private List<Date> dateList=new ArrayList<>();
    private List<Double>strategyEarningList=new ArrayList<>();
    private List<Double>standardEarningList=new ArrayList<>();
    private List<List<String>> buyCodeList=new ArrayList<>();
    private String evaluate="";
    //private List<Double>yieldDistribution_x;
    //private List<Integer>yieldDistributin_y;

    public QuantitativeVO(Map<Date,Double>strategyEarning,Map<Date,Double>standardEarning,double annualizedReturn,double standardAnnualizedReturn,
                          double maximumDrawdown, double beta,double alpha,double sharpeRatie,Map<Double,Integer>yieldDistributionMap,List<List<String>> buyCodeList){
        if(strategyEarning!=null) {
            Iterator<Map.Entry<Date, Double>> iterator = strategyEarning.entrySet().iterator();
            Map.Entry<Date, Double> tail = null;
            while (iterator.hasNext()) {
                tail = iterator.next();
                dateList.add(tail.getKey());
                strategyEarningList.add(tail.getValue());
            }
            Iterator<Map.Entry<Date, Double>> iterator2 = standardEarning.entrySet().iterator();
            Map.Entry<Date, Double> tail2 = null;
            while (iterator2.hasNext()) {
                tail2 = iterator2.next();
                standardEarningList.add(tail2.getValue());
            }
        }
        this.standardAnnualizedReturn=standardAnnualizedReturn;
        this.annualizedReturn=annualizedReturn;
        this.maximumDrawdown=maximumDrawdown;
        this.Beta=beta;
        this.Alpha=alpha;
        this.sharpeRatie=sharpeRatie;
        this.yieldDistributionMap=yieldDistributionMap;
        this.buyCodeList=buyCodeList;
    }
    public QuantitativeVO(){

    }

    public List<Double> getStrategyEarningList() {
        return strategyEarningList;
    }

    public List<Double> getStandardEarningList() {
        return standardEarningList;
    }

    public List<Date> getDateList() {
        return dateList;
    }

    public double getAnnualizedReturn() {
        return annualizedReturn;
    }

    public double getStandardAnnualizedReturn() {
        return standardAnnualizedReturn;
    }

    public double getMaximumDrawdown() {
        return maximumDrawdown;
    }

    public double getAlpha() {
        return Alpha;
    }

    public double getBeta() {
        return Beta;
    }

    public double getSharpeRatie() {
        return sharpeRatie;
    }

    public Map<Double, Integer> getYieldDistributionMap() {
        return yieldDistributionMap;
    }

    public List<List<String>> getBuyCodeList() {
        return buyCodeList;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluate() {
        return evaluate;
    }
}
