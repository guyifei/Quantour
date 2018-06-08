package anyeight.model;

import java.util.Date;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/13.
 */
public class MarketInTime {
    /**
     * 股票ID列表
     */
    private List<Date> dateList;
    /**
     * 涨停股票个数列表
     */
    private List<Integer> hardenStock;
    /**
     * 跌停股票个数列表
     */
    private List<Integer> dropStock;

    public MarketInTime(){}

    public MarketInTime(List<Date> dateList,List<Integer> hardenStock, List<Integer> dropStock){
        this.dateList = dateList;
        this.hardenStock = hardenStock;
        this.dropStock = dropStock;
    }

    public List<Date> getDate() {
        return dateList;
    }

    public List<Integer> getOver5() {
        return hardenStock;
    }

    public List<Integer> getLower5() {
        return dropStock;
    }
}
