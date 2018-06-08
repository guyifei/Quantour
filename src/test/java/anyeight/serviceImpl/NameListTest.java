package anyeight.serviceImpl;

import org.junit.Test;

import java.util.Map;

/**
 * Created by T5-SK on 2017/6/12.
 */
public class NameListTest {
    @Test
    public void getMap(){
        Map map = NameList.getNameList().getMap();
        System.out.println("lal");
    }
}