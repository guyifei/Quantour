package anyeight.model;

import java.util.*;

/**
 * Created by 啊 on 2017/6/3.
 */
public class StockInTime {
//    股票ID
    private String ID;
//    时间和股票加个的对应
    private List<Map> mapList;



    public String getID(){
        return ID;
    }

    public Map<Date, Stock> getMap(){
        Map<java.util.Date,Stock>result=new TreeMap<>();
        for(int i=0;i<mapList.size();i++){
            Iterator<Map.Entry<java.util.Date, Stock>> iterator1 = mapList.get(i).entrySet().iterator();
            Map.Entry tail1 = iterator1.next();
            Stock value = (Stock) tail1.getValue();
            java.util.Date date = (java.util.Date) tail1.getKey();
            result.put(date,value);
        }
        return result;
    }
}
