package anyeight.serviceImpl.neuralLearning;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class testClassTest {

    @Autowired
    testClass testClass;

   @Test
    public void yest(){
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       Date date = null;
       Date date1 = null;
       Date date2 = null;
       try {
           date = sdf.parse("2014-02-01");
           date1 = sdf.parse("2015-12-01");
           date2 = sdf.parse("2017-06-12");
       } catch (ParseException e) {
           e.printStackTrace();
       }

       List list = testClass.NeuralLearn("000001",date,date1,date2);
       System.out.println();
   }
}