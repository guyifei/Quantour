package anyeight.jsonTrans;

import anyeight.model.*;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.StockVO;
import com.google.gson.Gson;

import java.util.*;

/**
 * Created by Administrator on 2017/5/16.
 * 用于json的转换
 */
public class jsonTrans {
    Gson gson=new Gson();

    /**
     * 将股票数据转化为绘制K线图用的json格式
     * @param stocks
     * @return
     */
    public String stockTrans(List<StockVO> stocks){
        int size=stocks.size();
        Object[] objs=new Object[size];
        for(int i=0;i<size;i++){
            StockVO tempStock=stocks.get(i);
            Object[] obj={
                    tempStock.getDate(),tempStock.getOpen(),tempStock.getClose(),
                    tempStock.getLow(),tempStock.getHigh(),tempStock.getVolome()
            };
            objs[i]=obj;
        }
        String str=gson.toJson(objs);
        return str;
    }

    /**
     * 股票比较时用到的json转化
     * @param stocks
     * @return
     */
    public String stockCompare(List<StockVO> stocks){
        int size=stocks.size();
        Object[] objs=new Object[size];
        for(int i=0;i<size;i++){
            StockVO tempStock=stocks.get(i);
            Object[] obj={
                    tempStock.getDate(),tempStock.getOpen(),tempStock.getClose(),
                    tempStock.getLow(),tempStock.getHigh()
            };
            objs[i]=obj;
        }
        String str=gson.toJson(objs);
        return str;
    }

    /**
     * 将市场指数数据转化为绘制K线图用的json格式
     * @param markets
     * @return
     */
    public String marketTrans(List<K_Market> markets){
        int size=markets.size();
        Object[] objs=new Object[size];
        for(int i=0;i<size;i++){
            K_Market tempMarket=markets.get(i);
            Object[] obj={
                    tempMarket.getDate(),tempMarket.getOpen(),tempMarket.getClose(),
                    tempMarket.getLow(),tempMarket.getHigh(),tempMarket.getVolome()
            };
            objs[i]=obj;
        }
        String str=gson.toJson(objs);
        return str;
    }

    /**
     * 用于绘制市场热度折线图
     * @param markets
     * @return
     */
    public String maketLine(List<Market> markets){
        int size=markets.size();
        List<Date> dateList=new ArrayList<>();
        List<Integer> hardenStock=new ArrayList<>();
        List<Integer> dropStock=new ArrayList<>();
        for(int i=0;i<size;i++){
            dateList.add(markets.get(i).getDate());
            hardenStock.add(markets.get(i).getHardenStock());
            dropStock.add(markets.get(i).getDropStock());
        }
        MarketInTime marketInTime=new MarketInTime(dateList,hardenStock,dropStock);
        return gson.toJson(marketInTime);
    }

    /**
     * 用于绘制回测柱状图
     * @param vo
     * @return
     */
    public String computeTrans(QuantitativeVO vo){
        List<Double> xAxis=new ArrayList<>();
        List<Integer> pAxis=new ArrayList<>();
        List<Integer> nAxis=new ArrayList<>();
        int positive=0;
        int negative=0;
        Map<Double,Integer>yieldDistributionMap=vo.getYieldDistributionMap();
        Iterator<Map.Entry<Double,Integer>> iterator = yieldDistributionMap.entrySet().iterator();
        Map.Entry<Double,Integer> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
            xAxis.add(tail.getKey()/100);
            if(tail.getKey()>0){
                pAxis.add(tail.getValue());
                nAxis.add(0);
                positive++;
            }else {
                pAxis.add(0);
                nAxis.add(tail.getValue());
                negative++;
            }
        }
        double rate=(double) positive/(double)(positive+negative);
        computeTemp temp=new computeTemp(vo.getAnnualizedReturn(),vo.getStandardAnnualizedReturn(),vo.getMaximumDrawdown(),
                String.valueOf(vo.getBeta()),String.valueOf(vo.getAlpha()),String.valueOf(vo.getSharpeRatie()),vo.getDateList(),vo.getStrategyEarningList(),vo.getStandardEarningList(),
                positive,negative,rate,xAxis,pAxis,nAxis,vo.getEvaluate());
        return new Gson().toJson(temp);
    }

    /**
     * 用于绘制神经网络的json转换
     * @param resultList
     * @return
     */
    public String neuralTrans(List<ArrayList<Double>> resultList,List<ArrayList<Double>> earnList){
        List<Integer> dateList=new ArrayList<>();
        List<Double> expect=new ArrayList<>();
        List<Double> target=new ArrayList<>();
        List<Double> earn=new ArrayList<>();
        int size=earnList.size();
        for(int i=0;i<size;i++){
            dateList.add(i);
            expect.add(resultList.get(i).get(0));
            target.add(resultList.get(i).get(1));
            earn.add(earnList.get(i).get(0));
        }
        neuralClass neural=new neuralClass(dateList,expect,target,earn);
        return new Gson().toJson(neural);

    }

    /**
     * 用户基本信息时用到的json转化
     * @param userInfo
     * @return
     */
    public String userInfoTrans(UserInfo userInfo){
        String str=gson.toJson(userInfo);
        return str;
    }

    /**
     * 用户基本信息时用到的json转化
     * @param user_collect
     * @return
     */
    public String collectInfoTrans(User_Collect user_collect){
        String str=gson.toJson(user_collect);
        return str;
    }

    /**
     * 自选股票时用到的json转化
     * @return
     */
    public String treeTrans(){
        return "";
    }

    /**
     * 用于树结构json的类
     */
    class tree{
        private String[] text;
        private String[] nodes;

        public tree(String[] text,String[] nodes){
            this.text=text;
            this.nodes=nodes;

        }
    }

    class neuralClass{
        private List<Integer> dateList=new ArrayList<>();
        private List<Double> expect=new ArrayList<>();              //拟合曲线
        private List<Double> target=new ArrayList<>();              //实际收盘价
        private List<Double> earn=new ArrayList<>();                //算法收益
        public neuralClass(List<Integer> dateList,List<Double> expect,List<Double> target,List<Double> earn){
            this.dateList=dateList;
            this.expect=expect;
            this.target=target;
            this.earn=earn;
        }
    }

    class computeTemp{
        private double annualizedReturn;                                   //年收益率
        private double standardAnnualizedReturn;                           //年基准收益率
        private double maximumDrawdown;                                      //最大回撤
        private String Beta;
        private String Alpha;
        private String sharpeRatie;                                           //夏普比率
        private List<Date> dateList=new ArrayList<>();
        private List<Double>strategyEarningList=new ArrayList<>();
        private List<Double>standardEarningList=new ArrayList<>();
        private int positive;
        private int negative;
        private double rate;
        private List<Double> cateAxis=new ArrayList<>();
        private List<Integer> pAxis=new ArrayList<>();
        private List<Integer> nAxis=new ArrayList<>();
        private String analyze;

        public computeTemp(double annualizedReturn,double standardAnnualizedReturn,double maximumDrawdown,String Beta,
                           String Alpha,String sharpeRatie,List<Date> dateList,List<Double>strategyEarningList,
                           List<Double>standardEarningList,int positive,int negative,double rate,List<Double> xAxis,
                           List<Integer> pAxis,List<Integer> nAxis,String analyze){
            this.annualizedReturn=annualizedReturn;
            this.standardAnnualizedReturn=standardAnnualizedReturn;
            this.maximumDrawdown=maximumDrawdown;
            this.Beta=Beta;
            this.Alpha=Alpha;
            this.sharpeRatie=sharpeRatie;
            this.dateList=dateList;
            this.strategyEarningList=strategyEarningList;
            this.standardEarningList=standardEarningList;
            this.positive=positive;
            this.negative=negative;
            this.rate=rate;
            this.cateAxis=xAxis;
            this.pAxis=pAxis;
            this.nAxis=nAxis;
            this.analyze=analyze;

        }

    }
}
