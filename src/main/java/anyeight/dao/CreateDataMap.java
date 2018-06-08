package anyeight.dao;

import anyeight.model.Market;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/7.
 */
public interface CreateDataMap {
    /*预处理数据，将每天的最热门的九个版块添加到数据库中*/
    public void addMarketHost(Date date,String first,String second,String third,String fourth,String fifth,String sixth,String seventh,String eighth,String ninth);
    /*添加市场温度计数据进入数据库中*/
    public void addMarket(Date date,@Param("market") Market market);
    /*获得所有的股票ID*/
    public List<String> getAllID();
    /*更新股票的 主板归属等数据*/
    public void updatePlate(String code,String plate);
}
