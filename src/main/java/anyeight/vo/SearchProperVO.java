package anyeight.vo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 段梦洋 on 2017/4/16.
 */
public class SearchProperVO {
    private List<Integer> excessEarning_x=new ArrayList<>();    //超额收益率X轴
    private List<Double> excessEarning_y=new ArrayList<>();     //超额收益率Y轴
    private List<Integer> strategyWin_x=new ArrayList<>();      //策略胜率X轴
    private List<Double> strategyWin_y=new ArrayList<>();       //策略胜率Y轴
   // private Map<Integer,Double> excessEarning;
  //  private Map<Integer,Double> strategyWin;
    public SearchProperVO(Map map1,Map map2){
        Iterator<Map.Entry<Integer,Double>> iterator = map1.entrySet().iterator();
        Map.Entry<Integer,Double> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
            excessEarning_x.add(tail.getKey());
            excessEarning_y.add(tail.getValue());
        }
        Iterator<Map.Entry<Integer,Double>> iterator2 = map2.entrySet().iterator();
        Map.Entry<Integer,Double> tail2 = null;
        while (iterator2.hasNext()) {
            tail2 = iterator2.next();
            strategyWin_x.add(tail2.getKey());
            strategyWin_y.add(tail2.getValue());
        }
    }
    public SearchProperVO(){

    }

    public List<Integer> getExcessEarning_x() {
        return excessEarning_x;
    }

    public List<Double> getExcessEarning_y() {
        return excessEarning_y;
    }

    public List<Integer> getStrategyWin_x() {
        return strategyWin_x;
    }

    public List<Double> getStrategyWin_y() {
        return strategyWin_y;
    }
}
