package anyeight.serviceImpl;

import anyeight.service.FindProperSectionService;
import anyeight.util.SelectType;
import anyeight.util.StrategyType;
import anyeight.vo.SearchProperVO;
import anyeight.vo.SelectVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/6/10.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class FindProperSectionServiceImplTest {
    @Autowired
    FindProperSectionService findProperSectionService;

    @Test
    public void findPropperHold() {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate1=null;
        Date myDate2=null;
        try {
            myDate1 = dateFormat1.parse("2016-10-01");
            myDate2 = dateFormat1.parse("2016-12-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SelectVO selectVO=new SelectVO(StrategyType.MOMENTUM, SelectType.MAXTOMIN,0.05,0,0,0);
        List<SelectVO> selectVOs=new ArrayList<>();
        selectVOs.add(selectVO);
        SearchProperVO searchProperVO=findProperSectionService.findPropperHold(myDate1,myDate2,5,selectVOs);
        System.out.println("success");
    }

    @Test
    public void findProperFormative() {

    }

}