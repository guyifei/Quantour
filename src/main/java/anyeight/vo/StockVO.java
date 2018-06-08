package anyeight.vo;

import anyeight.model.Stock;
import anyeight.util.MathHelper;

import java.util.Date;

/**
 * Created by 啊 on 2017/5/16.
 */
public class StockVO {
    private Date date;      //信息属于的日期
    private double open;    //当日开盘价
    private double high;    //当日最高价
    private double low;     //当日最低价
    private double close;   //当日收盘价
    private long volome;    //当日成交总量
    private double adjClose;    //当日复权收盘价
    private String id;      //股票ID
    private String name;    //股票名
    private String market;  //股票所属行业

    public StockVO(Stock stock){
        this.date= MathHelper.sqlToUtil(stock.getDate());
        this.open=stock.getOpen();
        this.high=stock.getHigh();
        this.low=stock.getLow();
        this.close=stock.getClose();
        this.volome=stock.getVolome();
        this.adjClose=stock.getAdjClose();
        this.id=stock.getCode();
        this.name=stock.getName();
        this.market=stock.getMarket();
    }
    public StockVO(Date date,double open,double high,double low,double close,long volome,double adjClose,String id,String name,String marke){
        this.date=date;
        this.open=open;
        this.high=high;
        this.low=low;
        this.close=close;
        this.volome=volome;
        this.adjClose=adjClose;
        this.id=id;
        this.name=name;
        this.market=market;
    }
    public Date getDate(){
        return this.date;
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

    public long getVolome() {
        return volome;
    }

    public double getAdjClose() {
        return adjClose;
    }

    public double getClose() {
        return close;
    }

    public String getCode() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMarket() {
        return market;
    }
}
