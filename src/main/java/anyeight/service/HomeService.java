package anyeight.service;

import anyeight.model.K_Market;
import anyeight.model.News;
import anyeight.model.Stock;

import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/5/18.
 */
public interface HomeService {
    /*在一天内最热门的三只股票*/
    public List<Stock> getHottestStock(Date date, String plate);
    /*在市场K线图*/
    public K_Market getK_Market(Date date);
    /*获得最热门的九个板块*/
    public List<String> getHottestMarket(Date date);
    /*获得最新的二十条即时新闻*/
    public List<News> getNewsInstantNews();
}
