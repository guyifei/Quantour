package anyeight.model;

import java.util.Date;

/**
 * Created by 啊 on 2017/5/14.
 */
public class Market {
    private long totalVolume;           //总成交量
    private int hardenStock;            //涨停股票
    private int dropStock;                //跌停股票
    private int over5Stock;               //涨幅超过百分之五的股票
    private int down5Stock;              //跌幅超过百分之五的股票
    private int over5PriceStock;           //开盘‐收盘大于 5%*上一个交易日收盘价的 股票个数
    private int down5PriceStock;          //开盘‐收盘小于 5%*上一个交易日收盘价的 股票个数
    private Date date;                  //市场温度计的对应时间



    public Market(){

    }
    public Market(long totalVolume,int hardenStock,int dropStock,int over5Stock,int down5Stock,int over5PriceStock,int down5PriceStock){
        this.totalVolume=totalVolume;
        this.hardenStock=hardenStock;
        this.dropStock=dropStock;
        this.over5Stock=over5Stock;
        this.down5Stock=down5Stock;
        this.over5PriceStock=over5PriceStock;
        this.down5PriceStock=down5PriceStock;
    }

    public long getTotalVolume() {
        return totalVolume;
    }

    public int getHardenStock() {
        return hardenStock;
    }

    public int getDropStock() {
        return dropStock;
    }

    public int getOver5Stock() {
        return over5Stock;
    }

    public int getDown5Stock() {
        return down5Stock;
    }

    public int getOver5PriceStock() {
        return over5PriceStock;
    }

    public int getDown5PriceStock() {
        return down5PriceStock;
    }

    public void setTotalVolume(long totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Date getDate() {
        return date;
    }
}
