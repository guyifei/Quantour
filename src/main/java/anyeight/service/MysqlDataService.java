package anyeight.service;

/**
 * Created by T5-SK on 2017/6/9.
 */
public interface MysqlDataService {
    /*最热门的九个板块的预处理*/
    public void marketData();
    /*市场温度计数据的预处理*/
    public void MarketModel();
    /*分版块*/
    public void split();
    /**
     * 测试Python链接
     */
    public int pythonTest();

    /**
     * python包无法导入，计划破产
     */
    public void predictStock();
    /**
     * 神经网络学习
     */
    public void weka();
}
