package anyeight.model;

import java.util.Date;

/**
 * Created by T5-SK on 2017/6/13.
 */
public class Dragon {
    /**
     * 股票代码
     */
    private String code;
    /**
     * 股票名字
     */
    private String name;
    /**
     * 当日涨跌幅
     */
    private double pchange;
    /**
     * 龙虎榜成交额(万)
     */
    private double amount;
    /**
     * 买入额(万)
     */
    private double buy;
    /**
     * 买入占总成交比例
     */
    private double bratio;
    /**
     * 卖出额(万)
     */
    private double sell;
    /**
     * 卖出占总成交比例
     */
    private double sratio;
    /**
     * 上榜原因
     */
    private String reason;
    /**
     * 日期
     */
    private Date date;


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPchange() {
        return pchange;
    }

    public double getAmount() {
        return amount;
    }

    public double getBuy() {
        return buy;
    }

    public double getBratio() {
        return bratio;
    }

    public double getSell() {
        return sell;
    }

    public double getSratio() {
        return sratio;
    }

    public String getReason() {
        return reason;
    }

    public Date getDate() {
        return date;
    }
}
