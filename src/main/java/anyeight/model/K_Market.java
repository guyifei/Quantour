package anyeight.model;

import java.util.Date;

/**
 * Created by 啊 on 2017/5/18.
 */
//D
public class K_Market {
    /**
     * 时间
     */
    private Date date;
    /**
     * 开盘价
     */
    private double open;
    /**
     * 最高价
     */
    private double high;
    /**
     * 最低价
     */
    private double low;
    /**
     * 收盘价
     */
    private double close;
    /**
     * 交易量
     */
    private long volome;
    public K_Market(){

    }
    public K_Market(Date date,double open,double high,double low,double close,long volome){
        this.date=date;
        this.open=open;
        this.high=high;
        this.low=low;
        this.close=close;
        this.volome=volome;
    }

    public Date getDate() {
        return date;
    }

    public double getOpen() {
        return open;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getClose() {
        return close;
    }

    public long getVolome() {
        return volome;
    }
}
