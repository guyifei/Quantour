package anyeight.model;


import java.sql.Date;

public class Stock {
//    日期
    private Date date;
//    开盘价
    private double open;
//    最高价
    private double high;
//    最低价
    private double low;
//    收盘价
    private double close;
//    交易量
    private long volome;
//    复权收盘价
    private double adjClose;
//    股票ID
    private String id;
//    股票名字
    private String name;
//    股票市场
    private String market;
//    换手率
    private double turnover;


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

    public double getTurnover(){return turnover;}
}
