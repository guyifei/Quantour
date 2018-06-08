package anyeight.dao;

import anyeight.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface StockMapper {
    /*给定股票名，返回股票id*/
    public String getStockId(String str);

    /*一段时间内，一只股票的所有表现*/
    public List<Stock> getOneStock(String ID, Date beginDate, Date endDate);

    /**
     * 获得一个股票距结束时间最近的一天的StockVO
     */
    public Stock getRecentlyStock(String id, Date endDate);


    /*某一天中，所有股票的具体表现*/
    public List<Stock> getOneDayStock(Date beginData);
    /* 返回股票名与id的对应map*/
    public List<Map> getIDList() ;
    /* 返回均线图信息*/
    public List<Average> getAverageList(String ID,Date beginDate, Date endDate) ;
    /*获取市场温度计*/
    public Market getMarket(Date date);

    /**
     * 获得一段时间内的市场温度计相关信息
     */
    public List<Market> getMarketInTime(Date dateStart,Date dateEnd);


    /*返回起始数据的时间*/
    //public Date getBeginDate() ;

    /*返回结束数据的时间*/
    //public Date getEndDate() ;

    /*返回一只股票的前n日交易日期,若id为空字符串，则返回大盘前n日交易日期,若为2001-1-1,则存在问题*/
    public Date getExCalendar(String id,Date date,@Param("day")int day);

    /*返回一只股票的后n日交易日期;若id为空字符串，则返回大盘后n日交易日期*/
    public Date getAfterCalendar(String id,Date date,@Param("day")int day);

    /*获取某个模块的股票代码，获取的list里面是ID*/
    public List<String> getStocksByPlate (String plate);

    /*获取某个模块的股票代码，获取的list里面是名字*/
    public List<String> getStocksNameByPlate(String plateName) ;

    /*获取某个模块在某段时间的累计收益率，若plate为"",则返回整个大盘在这段时间的累计收益率*/
    public List<Map> getStandardLine(Date start,Date end,String plate);

    /*返回list里所有有数据股票的信息*/
    public List<BuyAndSellPrice> getAvailableClose(Date start, Date end, @Param("ids")List<String>ids);

    /*返回list里所有有数据股票的信息*/
    public List<Stock> getStockList(Date start, Date end, @Param("ids")List<String>ids);

    /*返回一只股票输入日期的股票信息，以及他的上一个交易日的股票交易信息
    * 返回的ArrayList如果长度为零，说明没有当天或者上一天*/
    //public ArrayList<StockPO> getTodayAndExStock(String id, Calendar today) ;

   /*通过一个存有股票id的List，来获得在List里面所有股票的对应每天的复权收盘价*/
   public List<AdjClose> getAdjClose(@Param("ids") List<String> ids, Date start, Date end) ;

    /*得到用户自选股票的分类的类名*/
    public List<String> getBlockNameList();

    /*得到某天最热门的三只股票*/
    public List<Stock>getHottestStock(Date date,String plate);

    /*得到大盘K线图相关数据*/
    public K_Market getK_Market(Date date);




    /**
     *
     * @param
     * @return
     */
    public List<K_Market> getK_Market_More(String market, Date dateStart, Date dateEnd);

     /*实现模糊搜索功能，返回匹配的股票列表*/
     public List<Stock> getProperStocks(Date date,String str);

    /*实现模糊搜索功能，给出股票名片段返回匹配的股票列表*/
    public Boolean hasProperStocks(String str);

    /*最热门的三个板块*/
    public Map<String,String> getHottestMarket(Date date);

    /*实现联想输入功能，返回的是code符合的名字list*/
    public List<String> getAssociatingInputCode(String str);
    /*名字和code必须要分开，返回的是name符合搜索的list*/
    public List<String> getAssociatingInputName(String str);
    /*返回List，只含有code/id*/
    public List<String> getList();
    /*返回该天的十条牛股数据*/
    public List<Dragon> getDragon(Date date);
}