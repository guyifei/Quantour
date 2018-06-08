package anyeight.vo;

import anyeight.util.SelectType;
import anyeight.util.StrategyType;

/**
 * Created by 啊 on 2017/6/7.
 */
public class SelectVO {
    private StrategyType strategyType;  //用户自定义策略的选股指标
    private SelectType selectType;      //每个指标的选择方式，从大到小，从小到大，上限，下限，范围
    private double proportion;          //筛选前百分之多少的股票的, 缺省值
    private int order;      //顺序，从大到小为1，从小到大为0
    private double max;     //上限
    private double min;     //下限
    public SelectVO(StrategyType strategyType, SelectType selectType,double proportion, int order, double max, double min){
        this.strategyType = strategyType;
        this.selectType=selectType;
        this.proportion=proportion;
        this.order=order;
        this.max=max;
        this.min=min;
    }

    public double getProportion() {
        return proportion;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public double getMax() {
        return max;
    }

    public int getOrder() {
        return order;
    }

    public double getMin() {
        return min;
    }

    public SelectType getSelectType() {
        return selectType;
    }
}
