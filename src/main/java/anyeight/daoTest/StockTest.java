package anyeight.daoTest;

import anyeight.dao.StockMapper;
import anyeight.model.*;
import anyeight.service.HomeService;
import anyeight.service.StockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by T5-SK on 2017/5/14.
 */

@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class StockTest {
    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockService stockService;

    @Autowired
    private HomeService homeService;

    @Test
    public void testGetListMap(){
        Map map = stockService.getIDList();
        System.out.println("over");
    }

    @Test
    public void testGetStockImpl(){
        System.out.println(stockMapper.getStockId("京能置业"));
    }

    @Test
    public void testGetOneStock(){
        Date date1 = Date.valueOf("2017-05-02");
        Date date2 = Date.valueOf("2017-05-05");
        List list = stockMapper.getOneStock("600743",date1,date2);
        int i = list.size();
        System.out.println(i);
    }

    @Test
    public void testService(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        java.util.Date date2 = null;

        try {
            date1 = sdf.parse("2017-05-08");
            date2 = sdf.parse("2017-05-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int i = stockService.getStockInfo("华远地产",date1,date2).size();
        System.out.println(i);
    }

    @Test
    public void testGetOneDayStock(){
        Date date1 = Date.valueOf("2017-06-07");
        int i = stockMapper.getOneDayStock(date1).size();
        System.out.println(i);
    }

    @Test
    public void testGetHottestStock(){
        Date date = Date.valueOf("2017-06-13");
        List<Stock> strings = stockMapper.getHottestStock(date,"陶瓷");

        for (Stock str: strings){
            System.out.println(str.getName());
            System.out.println(str.getVolome());
        }
    }

    @Test
    public void testGetIDList(){
        List<Map> map = stockMapper.getIDList();
        System.out.println(map.size());

    }

    @Test
    public void testGetAverageList(){
        Date date1 = Date.valueOf("2017-5-2");
        Date date2 = Date.valueOf("2017-5-10");
        List<Average> list = stockMapper.getAverageList("000001",date1,date2);
        System.out.println(list.size());
    }

    @Test
    public void testGetExCanlendar(){
        Date date = Date.valueOf("2010-02-09");
        Date ans = stockMapper.getExCalendar("",date,1);
        System.out.println(ans.toString());
    }

    @Test
    public void testGetAfterCanlendar(){
        Date date = Date.valueOf("2017-05-02");
        Date ans = stockMapper.getAfterCalendar("",date,4);
        System.out.println(ans.toString());
    }

    @Test
    public void testGetStocksNameByPlate(){
        stockMapper.getStocksNameByPlate("影视音像");
    }

    @Test
    public void testGetAdjClose(){
        Date date1 = Date.valueOf("2017-5-2");
        Date date2 = Date.valueOf("2017-5-10");

        List ids = new ArrayList();
//        Map map = stockService.getIDList();
//        Set set = map.keySet();
//        for(Object line:set){
//            ids.add(map.get(line));
//        }

        ids.add("000001");



        List list = stockMapper.getAdjClose(ids,date1,date2);
        AdjClose adjClose = (AdjClose) list.get(0);
        Map map = adjClose.getMap();
        System.out.println("over");

    }

    @Test
    public void testGetBlockNameList(){
        List<String> list = stockMapper.getBlockNameList();

        for(String line : list){
            stockMapper.getStocksNameByPlate(line);
        }
        System.out.println("over");
    }

    @Test
    public void testGetCompleteName(){
//        List<String> list = stockMapper.getCompleteName("龙地");
//        System.out.println(list.size());
    }

    @Test
    public void testGetAvailableClose(){
        Date date1 = Date.valueOf("2014-05-19");
        Date date2 = Date.valueOf("2014-05-20");
        List<String> lalal = new ArrayList<>();
        lalal.add("000001");
//        lalal.add("000002");
        List<BuyAndSellPrice> list = stockMapper.getAvailableClose(date1,date2,lalal);
        System.out.println(list.size());
    }

    @Test
    public void testGetK_Market(){
        Date date1 = Date.valueOf("2005-02-01");
        K_Market k_market = stockMapper.getK_Market(date1);
        System.out.println("success");
    }

    @Test
    public void testGetStandardLine(){
        Date date = Date.valueOf("2016-06-01");
        Date date1 = Date.valueOf("2016-09-01");
        List list = stockMapper.getStandardLine(date,date1,"sh000001");
        System.out.println(list.size());
        Map map = (Map) list.get(1);
        Set set = map.keySet();
        for(Object ob:set){
            System.out.println(ob);
            System.out.println(map.get(ob));
        }
    }

    @Test
    public void testHasProperStocks(){
        System.out.println(stockMapper.hasProperStocks("江苏"));
    }

    @Test
    public void testGetProperStocks(){
        Date date = Date.valueOf("2017-05-03");
        List<Stock> list = stockMapper.getProperStocks(date,"区域地产");
        for (Stock stock: list){
            System.out.println(stock.getName());
        }
    }

    @Test
    public void testGetProperStocksById(){
//        Date date = Date.valueOf("2016-06-01");
//        List<Stock> list = stockMapper.getProperStocksById(date,"600743");
//        System.out.println(list.size());
    }

    @Test
    public void testGetProperStocksByName(){
//        Date date = Date.valueOf("2017-05-10");
//        List<Stock> list = stockMapper.getProperStocksByName(date,"华");
//        for(Stock stock:list){
//            System.out.println(stock.getName());
//        }
    }

    @Test
    public void testGetHottestMarketInHomeService(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = sdf.parse("2017-06-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<String> list = homeService.getHottestMarket(date);
        System.out.println(list.size());
    }

    @Test
    public void testGetHostMarket(){
//        Date date = Date.valueOf("2017-05-07");
//        Map<String,String> map = stockMapper.getHottestMarket(date);
//        System.out.println("lala");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = sdf.parse("2017-06-07");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List list = homeService.getHottestMarket(date);
        System.out.println("lala");
    }

    @Test
    public void testGetMarket(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = sdf.parse("2017-06-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Market market = stockService.getMarketInfo(date);
        System.out.println("success");
    }


    @Test
    public void testGetAssociatingInput(){
        List<String> list = stockMapper.getList();
        for (String line:list){
            System.out.println(line);
        }
    }

    @Test
    public void testGetStockList(){

//        stockMapper.getStockList()
    }

    @Test
    public void testGetMarketInTime(){
        Date date = Date.valueOf("2017-06-01");
        Date date1 = Date.valueOf("2017-06-05");

        List<Market> marketInTime = stockMapper.getMarketInTime(date,date1);
        System.out.println();
    }

    @Test
    public void testGetRencentStock(){
        Date date = Date.valueOf("2017-06-04");
        Stock stock = stockMapper.getRecentlyStock("000001",date);
        System.out.println();
    }

}
