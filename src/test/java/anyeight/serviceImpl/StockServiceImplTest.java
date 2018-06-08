package anyeight.serviceImpl;

import anyeight.model.Market;
import anyeight.service.StockService;
import anyeight.vo.StockVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 啊 on 2017/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class StockServiceImplTest {
    @Autowired
    StockService stockService;

    @Test
    public void getStockInfo() throws Exception {
        //2017-5-8 600743
        StockService stockService=new StockServiceImpl();
        Date date=new Date(2017,5,8);
        List<StockVO> stocks=stockService.getStockInfo("600743",date,date);
        StockVO stock=stocks.get(0);
        System.out.println(stock.getOpen());
        System.out.println(stock.getCode());
        System.out.println(stock.getHigh());
    }

    @Test
    public void getAI(){
        List list = stockService.getAssociatingInput("");
        System.out.println("lala");
    }

    @Test
    public void testGetK_markrt(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date date1 = null;
        try {
            date = sdf.parse("2017-06-05");
            date1 = sdf.parse("2017-06-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List list = stockService.getMarket("上证指数",date,date1);
        List newList = stockService.getMarket("A股指数",date,date1);
        System.out.println("lala");
    }

    @Test
    public void writeFile(){
        Map<String,String> map = stockService.getIDList();
        File file = new File("NameCodeMap.txt");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> set = map.keySet();
        String line = "";
        for(String name:set){
            line = name + "_" + map.get(name) + "\n";
            try {
                bw.write(line);
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMarketInTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Date date1 = null;
        try {
            date = sdf.parse("2017-06-01");
            date1 = sdf.parse("2017-06-05");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Market> markets = stockService.getMarketInTime(date,date1);
        System.out.println("lal");
    }

}