package anyeight.model;

import java.util.*;

/**
 * Created by 啊 on 2017/6/7.
 */
public class AdjClose {
    /**
     * 股票ID*/
    private String ID;
    /**
     * 时间和复权收盘价的对应关系
     */
    private List<Map> mapList;

    public AdjClose(){};

    public AdjClose(String ID, List<Map>mapList){
        this.ID = ID;
        this.mapList = mapList;
    }

    public String getID(){
        return ID;
    }

    public Map<Date, Double> getMap(){
        Map<Date, Double> result=new TreeMap<>();
        for(int i=0;i<mapList.size();i++){
            Map tempMap=mapList.get(i);
            Iterator iterator = tempMap.entrySet().iterator();
            Map.Entry entry = (java.util.Map.Entry)iterator.next();
            double value = (double) entry.getValue();
            entry = (java.util.Map.Entry)iterator.next();
            Date key = (Date)entry.getValue();
            result.put(key,value);
        }
        return result;
    }
}
