package anyeight.serviceImpl.strategy;

import anyeight.dao.StockMapper;
import anyeight.model.Stock;
import anyeight.util.LineType;
import anyeight.util.MathHelper;
import anyeight.util.SelectType;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 啊 on 2017/5/14.
 */
@Service
public class Strategy {
    @Autowired
    StockMapper stockMapper;

    protected Map<Date,Double>strategyEarning=new TreeMap<>();
    protected Map<Date,Double> standardEarning=new TreeMap<>();
    protected double annualizedReturn;
    protected double standardAnnualizedReturn;
    protected double maximumDrawdown=0;
    protected double Beta;
    protected double Alpha;
    protected double sharpeRatio;
    protected Map<Double,Integer>yieldDistributionMap=new TreeMap<>();


    private ArrayList<Double> strategyYieldRate=new ArrayList<>();              //每个持有期的策略收益率
    private ArrayList<Double> standardYieldRate=new ArrayList<>();              //每个持有期的标准收益率
    private final double riskFreeRate=0.175;
    private double strategyMoneyPerHold=100.0;                                          //每个持有期后所持有的钱数
    private double standardMoneyPerHold=100.0;
    private final double chengBen=100.0;                                       //总成本
    private ArrayList<Date>endHoldDate=new ArrayList<>();

    public Strategy(){

    }

    protected List selectStock(Map oldMap, SelectVO selectVO) {                                                              //对股票进行排序并返回前20%的股票
        ArrayList<String>result=new ArrayList<>();
        if(selectVO.getSelectType().equals(SelectType.MAX)){
            Iterator<Map.Entry<String, Double>> it = oldMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Double> entry = it.next();
                if(entry.getValue()<=selectVO.getMax())
                    result.add(entry.getKey());
            }
        }
        else if(selectVO.getSelectType().equals(SelectType.MIN)){
            Iterator<Map.Entry<String, Double>> it = oldMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Double> entry = it.next();
                if(entry.getValue()>=selectVO.getMin())
                    result.add(entry.getKey());
            }
        }
        else if(selectVO.getSelectType().equals(SelectType.RANGE)){
            Iterator<Map.Entry<String, Double>> it = oldMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Double> entry = it.next();
                if(entry.getValue()>=selectVO.getMin()&&entry.getValue()<=selectVO.getMax())
                    result.add(entry.getKey());
            }
        }
        else {
            ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(oldMap.entrySet());
            if (selectVO.getSelectType().equals(SelectType.MAXTOMIN)) {
                Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> arg0, Map.Entry<String, Double> arg1) {
                        return -(arg0.getValue().compareTo(arg1.getValue()));                                      //从大到小排序
                    }
                });
            } else{
                Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
                    @Override
                    public int compare(Map.Entry<String, Double> arg0, Map.Entry<String, Double> arg1) {
                        return arg0.getValue().compareTo(arg1.getValue());                                      //从大到小排序
                    }
                });
            }

            double size = (list.size() * selectVO.getProportion());
            String toSplit = "" + size;
            int integer = Integer.valueOf(toSplit.split("\\.")[0]);
            double xiaoShu = Double.valueOf("0." + toSplit.split("\\.")[1]);
            if (xiaoShu != 0)                                                                                //进一法
                integer++;
            for (int i = 0; i < integer; i++) {
                result.add(list.get(i).getKey());
            }
        }
        //System.out.println(result.size());
        return result;
    }

    protected void setLine(Date first, Date after, List<String> list,LineType type,Map<String,Double> openList){
        Map<Date,Double>dayGet=new TreeMap<>();
        double actualSize=0.0;
        for(int i=0;i<list.size();i++){
            if(openList.containsKey(list.get(i))) {
                double open=openList.get(list.get(i));
                List<Stock> stocks = stockMapper.getOneStock(list.get(i),MathHelper.utilToSql(first), MathHelper.utilToSql(after));
                for (int j = 0; j < stocks.size(); j++) {
                    Date date = stocks.get(j).getDate();
                   if(isSameDay(date,first)){
                       actualSize++;
                   }
                     //   actualSize++;
                    double perStockGet=stocks.get(j).getAdjClose()/open;
                    if (dayGet.containsKey(date)) {
                        dayGet.replace(date, dayGet.get(date) + perStockGet);
                    }
                    else
                        dayGet.put(date, perStockGet);
                }
            }
        }
        double moneyPerStock;
        if(type.equals(LineType.STRATEGY_LINE))
            moneyPerStock=strategyMoneyPerHold/actualSize;
        else
            moneyPerStock=standardMoneyPerHold/actualSize;
        Map<Date,Double>dayYield=new TreeMap<>();
        if(dayGet.size()!=0) {
            Iterator<Map.Entry<Date, Double>> iterator1 = dayGet.entrySet().iterator();
            Map.Entry<Date, Double> tail1 = null;
            while (iterator1.hasNext()) {
                tail1 = iterator1.next();
                dayYield.put(tail1.getKey(),(tail1.getValue()*moneyPerStock-chengBen)/chengBen);
            }
            if(type.equals(LineType.STRATEGY_LINE))
                strategyMoneyPerHold=tail1.getValue()*moneyPerStock;
            else
                standardMoneyPerHold=tail1.getValue()*moneyPerStock;
        }
        if(type.equals(LineType.STRATEGY_LINE)) {
            strategyEarning.putAll(dayYield);
            strategyYieldRate=getYieldList(LineType.STRATEGY_LINE);
        }
        else {
            standardEarning.putAll(dayYield);
            standardYieldRate=getYieldList(LineType.STANDARD_LINE);
        }
    }

    protected QuantitativeVO getAll(Date start, Date end,List<List<String>> totalBuyList){
        Iterator<Map.Entry<Date,Double>> iterator1 = strategyEarning.entrySet().iterator();
        Map.Entry<Date,Double> tail1 = null;
        double drawDown=0;
        double before=0;
        while (iterator1.hasNext()) {                                 //最大回撤率的计算
            tail1 = iterator1.next();
            drawDown=before-tail1.getValue();
            if(drawDown>maximumDrawdown)
                maximumDrawdown=drawDown;
            before=tail1.getValue();
        }
        double strategyYield=tail1.getValue();
        Iterator<Map.Entry<Date,Double>> iterator2 = standardEarning.entrySet().iterator();
        Map.Entry<Date,Double> tail2 = null;
        while (iterator2.hasNext()) {
            tail2 = iterator2.next();
        }
        double standardYield=tail2.getValue();
        int days=Integer.parseInt(String.valueOf((end.getTime()-start.getTime())/(1000*3600*24)));
        annualizedReturn=strategyYield/days*365;
        standardAnnualizedReturn=standardYield/days*365;       //基准年化收益率
        System.out.println("covariance"+MathHelper.getCovariance(strategyYieldRate,standardYieldRate));
        System.out.println("variance"+MathHelper.getVariance(standardYieldRate));
        Beta= MathHelper.getCovariance(strategyYieldRate,standardYieldRate)/MathHelper.getVariance(standardYieldRate);      //贝塔比率
        Alpha=annualizedReturn-riskFreeRate-Beta*Math.floor(standardAnnualizedReturn*riskFreeRate);                    //阿尔法比率
        sharpeRatio=(annualizedReturn-riskFreeRate)/Math.pow(MathHelper.getVariance(strategyYieldRate),0.5);              //夏普比率
        setYieldDistributionMap();
        QuantitativeVO quantitativeVO=new QuantitativeVO(strategyEarning,standardEarning, annualizedReturn,standardAnnualizedReturn,
                maximumDrawdown, Beta,Alpha,sharpeRatio,yieldDistributionMap,totalBuyList);
        return quantitativeVO;
    }

    protected Boolean setStandardLine(Date start, Date end,String plate){
        List<Map>mapList=stockMapper.getStandardLine(MathHelper.utilToSql(start),MathHelper.utilToSql(end),plate);
        Map<java.sql.Date,Double>tempStandardEarning=new TreeMap<>();
        for(int i=0;i<mapList.size();i++){
            Map tempMap=mapList.get(i);
            Iterator iterator = tempMap.entrySet().iterator();
            Map.Entry entry = (java.util.Map.Entry)iterator.next();
            Object key = entry.getValue();
            entry = (java.util.Map.Entry)iterator.next();
            Object value = entry.getValue();
            tempStandardEarning.put((java.sql.Date) key,(Double) value);
        }

        Iterator<Map.Entry<java.sql.Date,Double>> iterator1 = tempStandardEarning.entrySet().iterator();
        Map.Entry<java.sql.Date,Double> tail1 = null;
        while (iterator1.hasNext()) {
            tail1 = iterator1.next();
            standardEarning.put(MathHelper.sqlToUtil(tail1.getKey()),tail1.getValue());
        }
        if(standardEarning.size()==0)
            return false;
        else{
            standardYieldRate=getYieldList(LineType.STANDARD_LINE);
        }
        return true;
    }

    private ArrayList<Double> getYieldList(LineType type){
        ArrayList<Double>result=new ArrayList<>();
        if(strategyEarning==null||endHoldDate==null){
            System.out.println();
        }
        if(endHoldDate.size()==0){
            System.out.println();
        }
        if(type.equals(LineType.STRATEGY_LINE)) {
            result.add(strategyEarning.get(endHoldDate.get(0)));
        }
        else {
            result.add(standardEarning.get(endHoldDate.get(0)));
        }
        if(endHoldDate.size()==1)
            return result;
        System.out.println(result==null?"null-yes":"null-no");
        System.out.println(result.size()==0?"size-yes":"size-no");
        double compare=result.get(0);
        double compare2=0;
        for(int i=1;i<endHoldDate.size();i++){
            if(type.equals(LineType.STRATEGY_LINE)) {
                if(!strategyEarning.containsKey(endHoldDate.get(i)))
                    continue;
                else
                    compare2=strategyEarning.get(endHoldDate.get(i));
            }
            else
                compare2=standardEarning.get(endHoldDate.get(i));
            result.add((compare2-compare)/(compare+1));
            compare=compare2;
        }
        return result;
    }
    protected void setEndHoldDate(Date calendar){
        endHoldDate.add(calendar);
    }

    private void setYieldDistributionMap(){
        for(int i=0;i<strategyYieldRate.size();i++){
            double rate=strategyYieldRate.get(i);
            int temp=(int)(rate/0.01);
            double actual=temp;
            DecimalFormat df=new DecimalFormat("0.0");
            actual=new Double(df.format(actual).toString());
            if(yieldDistributionMap.containsKey(actual))
                yieldDistributionMap.replace(actual,yieldDistributionMap.get(actual)+1);
            else
                yieldDistributionMap.put(actual,1);
        }
    }

    public Map getOpenPrice(Date date){                            //涨停或者这一天没有这只股票的数据
        List<Stock>stocks=new ArrayList<>();
////        Date exDate=stockMapper.getExCalendar(code,MathHelper.utilToSql(date),1);
////        if(exDate == null){
////            return -10000.0;
////        }
//        stocks=stockMapper.getOneStock(code,MathHelper.utilToSql(date),MathHelper.utilToSql(date));
//
//        if(stocks.size()<1){
//            return -10000.0;
//        }
//        double close=stocks.get(0).getAdjClose();
//
////        double exClose=stocks.get(1).getAdjClose();
////        double compare1=(close-exClose)*100/close;
////        double compare2=10-0.01*100/exClose;
////        if(compare1>=compare2)
////            return -20000.0;
////        else {
////            return close;
////        }
        stocks=stockMapper.getOneDayStock(MathHelper.utilToSql(date));
        Map<String,Double>openPrice=new HashMap<>();
        for(int i=0;i<stocks.size();i++){
            openPrice.put(stocks.get(i).getCode(),stocks.get(i).getAdjClose());
        }
        return openPrice;
    }

    public void release(){
        strategyEarning=new LinkedHashMap<>();
        standardEarning=new LinkedHashMap<>();
        yieldDistributionMap=new TreeMap<>();
        strategyYieldRate=new ArrayList<>();              //每个持有期的策略收益率
        standardYieldRate=new ArrayList<>();              //每个持有期的标准收益率
        strategyMoneyPerHold=100.0;                                          //每个持有期后所持有的钱数
       standardMoneyPerHold=100.0;
       endHoldDate=new ArrayList<>();
    }
    private boolean isSameDay(Date day1, Date day2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 =sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)){
            return true;
        } else{
            return false;
        }
    }

}
