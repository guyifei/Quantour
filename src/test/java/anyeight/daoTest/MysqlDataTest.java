package anyeight.daoTest;

import anyeight.service.MysqlDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by T5-SK on 2017/6/9.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class MysqlDataTest {
    @Autowired
    MysqlDataService mysqlDataService;

    @Test
    public void testMarketData(){
        mysqlDataService.marketData();
    }

    @Test
    public void testMarketModel(){
        mysqlDataService.MarketModel();
    }



    @Test
    public void testSplit(){
        mysqlDataService.split();
    }

    @Test
    public void testPython(){
        mysqlDataService.pythonTest();
    }

    @Test
    public void testPredictStock(){
        mysqlDataService.predictStock();
    }

    @Test
    public void testWeka(){
        mysqlDataService.weka();
    }
}