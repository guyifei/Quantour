package anyeight.service;

import anyeight.model.*;
import anyeight.vo.CompareVO;
import anyeight.vo.StockVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 啊 on 2017/5/11.
 */
public interface StockService {
    /**通过股票代码或者股票名称获取响应时间段内的股票信息*/
    public List<StockVO> getStockInfo(String idtentification, Date beginDate, Date endDate);

    /**
     * 获得一个股票距结束时间最近的一天的StockVO
     */
    public StockVO getRecentlyStock(String str,Date endDate);

    /**两只股票某一个时间段内的数据比较，传出数据，单只单只进行*/
    public CompareVO getCompare(String idtentification,Date beginDate, Date endDate);

    /**市场情况温度计，单日情况，大盘情况*/
    public Market getMarketInfo(Date date);

    /**
     * 获得一段时间内的市场温度计情况
     */
    public List<Market> getMarketInTime(Date dateStart, Date dateEnd);

    /*返回5，10,20,30,60天的平均收盘价 */
    public List<Average> getAverageVO(String idtentification, Date beginDate, Date endDate);

    /**
     * 返回股票名与id的对应list
     * */
    public  Map getIDList();

    /**
     * 实现模糊搜索功能，给出id或名字片段返回匹配的股票列表
     */
    public List<StockVO> getCompleteStocks(String str);

    /**
     * 实现联想输入功能
     * 结构为：ID + “/” + 名字
     */
    public List<String> getAssociatingInput(String str);

    /**
     * 获得上证指数，A股指数，综合指数的信息
     * @param market 取值为 上证指数，A股指数，综合指数
     * @return
     */
    public List<K_Market> getMarket(String market, Date dateStart,Date dateEnd);

    /**
     * 返回该天的十条牛股数据
     */
    public List<Dragon> getDragon(Date date);
}
