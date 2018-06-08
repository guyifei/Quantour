package anyeight.serviceImpl.strategy;

import java.util.Map;

/**
 * Created by 啊 on 2017/6/6.
 */
public class StrategyModel {
    private Map<String,Double> canBuyList;
    private Map<String, Double> yield;
    public StrategyModel(Map canBuyList, Map yield){
        this.canBuyList=canBuyList;
        this.yield=yield;
    }

    public Map<String, Double> getCanBuyList() {
        return canBuyList;
    }

    public Map<String, Double> getYield() {
        return yield;
    }
}
