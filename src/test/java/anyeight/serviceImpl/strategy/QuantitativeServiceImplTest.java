package anyeight.serviceImpl.strategy;

import anyeight.service.QuantitativeService;
import anyeight.util.MathHelper;
import anyeight.util.SelectType;
import anyeight.util.StrategyType;
import anyeight.vo.QuantitativeVO;
import anyeight.vo.SelectVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/6/6.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class QuantitativeServiceImplTest {
    @Qualifier("momentumStrategy")
    @Autowired
    QuantitativeService quantitativeService;
    @Qualifier("meanReverting")
    @Autowired
    QuantitativeService quantitativeService2;
    @Qualifier("customStrategy")
    @Autowired
    QuantitativeService quantitativeService3;
   @Test
    public void testGetAll(){
       DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
       Date myDate1=null;
       Date myDate2=null;
       try {
           myDate1 = dateFormat1.parse("2016-02-01");
           myDate2 = dateFormat1.parse("2016-06-01");
       } catch (ParseException e) {
           e.printStackTrace();
       }
        SelectVO selectVO=new SelectVO(StrategyType.MOMENTUM, SelectType.MAXTOMIN,0.05,0,0,0);
        List<SelectVO>selectVOs=new ArrayList<>();
        selectVOs.add(selectVO);
       QuantitativeVO quantitativeVO=quantitativeService.getAll(MathHelper.utilToSql(myDate1),MathHelper.utilToSql(myDate2),5,10,selectVOs);
       System.out.println(quantitativeVO.getAlpha());
   }
    @Test
    public void testGetAll3(){
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1=null;
        Date myDate2=null;
        try {
            myDate1 = dateFormat1.parse("2016-05-01");
            myDate2 = dateFormat1.parse("2016-06-15");




        } catch (ParseException e) {
            e.printStackTrace();
        }
        SelectVO selectVO1=new SelectVO(StrategyType.OPEN, SelectType.MIN,0,0,0,2);
        SelectVO selectVO2=new SelectVO(StrategyType.CLOSE, SelectType.MIN,0,0,0,2);
        SelectVO selectVO3=new SelectVO(StrategyType.ADJ_CLOSE, SelectType.MIN,0,0,0,2);;
        List<SelectVO>selectVOs=new ArrayList<>();
        selectVOs.add(selectVO1);
        selectVOs.add(selectVO2);
        selectVOs.add(selectVO3);
        QuantitativeVO quantitativeVO=quantitativeService3.getAll(MathHelper.utilToSql(myDate1),MathHelper.utilToSql(myDate2),5,10, selectVOs);
        System.out.println(quantitativeVO.getAlpha());
    }
    @Test
    public void testGetAll2(){
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1=null;
        Date myDate2=null;
        try {
            myDate1 = dateFormat1.parse("2016-06-01");
            myDate2 = dateFormat1.parse("2016-06-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SelectVO selectVO=new SelectVO(StrategyType.MEAN_REVERTING, SelectType.MAXTOMIN,0.05,0,0,0);
        List<SelectVO>selectVOs=new ArrayList<>();
        selectVOs.add(selectVO);
        QuantitativeVO quantitativeVO=quantitativeService2.getAll(MathHelper.utilToSql(myDate1),MathHelper.utilToSql(myDate2),5,10, selectVOs);
        System.out.println(quantitativeVO.getAlpha());
    }
}