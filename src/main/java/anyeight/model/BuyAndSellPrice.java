package anyeight.model;

/**
 * Created by 啊 on 2017/5/14.
 */
public class BuyAndSellPrice {
    /**
     * 开始日期的复权收盘价
     */
    private double startAdjClose;
    /**
     * 结束日期的复权收盘价
     */
    private double endAdjClose;
    /**
     * 股票ID
     */
    private String id;

    public BuyAndSellPrice(){

    }

    public double getStartAdjClose(){
        return startAdjClose;
    }
    public double getEndAdjClose(){
        return endAdjClose;
    }
    public String getId(){
        return id;
    }
}
