package anyeight.vo;

import java.util.List;

/**
 * Created by 啊 on 2017/6/9.
 */
public class CustomStrategyVO {
    private List<SelectVO> customStrategy;              //自定义策略
    private String strategyName;                        //策略名称

    public CustomStrategyVO(){}

    public CustomStrategyVO(List<SelectVO>selectVOs,String strategyName){
        this.customStrategy=selectVOs;
        this.strategyName=strategyName;
    }

    public List<SelectVO> getCustomStrategy() {
        return customStrategy;
    }

    public String getStrategyName() {
        return strategyName;
    }
}
