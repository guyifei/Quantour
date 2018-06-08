package anyeight.daoTest;

import anyeight.service.StockService;
import anyeight.serviceImpl.strategy.Strategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by T5-SK on 2017/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class CreateData {
    @Autowired
    Strategy strategy;
    @Autowired
    StockService stockService;

//    @Test
//    public void test() {
//        Map<String, String> map = stockService.getIDList();
//        Set<String> set = map.keySet();
//        ArrayList<String> list = new ArrayList<>();
//        File file = new File("F:\\大作业数据库\\code.txt");
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter(file));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        for(String line: set){
//            list.add(map.get(line));
//        }
//        Map<Date,String> ansMap = new HashMap<>();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = null;
//        Date dateStop = null;
//        try {
//            date = sdf.parse("2005-02-01");
//            dateStop = sdf.parse("2017-06-02");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < list.size(); i++){
//            while(date.before(dateStop)){
//                double canNotBuy = strategy.canNotBuy(list.get(i),date);
//                if(canNotBuy != -10000.0 & canNotBuy !=-20000.0){
//                    ansMap.put(date,"canBuy_" + list.get(0));
//                }else if(canNotBuy == -10000.0){
//                    ansMap.put(date,"notBuy_notContain_" + list.get(i));
//                }else if(canNotBuy == -20000.0){
//                    ansMap.put(date,"notBuy_harden_" + list.get(i));
//                }
//                Calendar cal = Calendar.getInstance();
//                cal.setTime(date);
//                cal.add(Calendar.DATE, 1);
//                date = cal.getTime();
//            }
//            Set<Date> ansSet = ansMap.keySet();
//            String str = null;
//            for(Date dateForWrite:ansSet){
//                str = ansMap.get(dateForWrite);
//                try {
//                    writer.write(sdf.format(dateForWrite) + "_" + str + "\n");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                writer.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            ansMap = new HashMap<>();
//            try {
//                date = sdf.parse("2005-02-01");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//
//        System.out.println("over");
//
//    }

}
