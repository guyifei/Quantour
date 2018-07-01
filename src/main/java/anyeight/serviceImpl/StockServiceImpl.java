package anyeight.serviceImpl;

import anyeight.dao.StockMapper;
import anyeight.model.*;
import anyeight.service.StockService;
import anyeight.util.MathHelper;
import anyeight.vo.CompareVO;
import anyeight.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 啊 on 2017/5/11.
 */
@Service
public class StockServiceImpl implements StockService{
    @Autowired
    private StockMapper stockMapper;

    public List<StockVO> getStockInfo(String idtentification, Date beginDate, Date endDate) {
        List<StockVO> stockVOs = new ArrayList<>();
        String id=getStockId(idtentification);
        if(id!=null) {
            List<Stock> stocks = stockMapper.getOneStock(id, MathHelper.utilToSql(beginDate), MathHelper.utilToSql(endDate));
            if (stocks != null) {
                for (int i = 0; i < stocks.size(); i++) {
                    stockVOs.add(new StockVO(stocks.get(i)));
                }
            }
            else{
                StockVO stock=new StockVO(null,-1,-1,-1,-1,-1,-1,"","","");
                stockVOs.add(stock);
            }
        }
        return stockVOs;
    }

    public StockVO getRecentlyStock(String str,Date endDate){
        String id=getStockId(str);
        StockVO stockVO = null;
        Stock stock = stockMapper.getRecentlyStock(id,MathHelper.utilToSql(endDate));
        if(stock != null){
            stockVO = new StockVO(stock);
        }else{
            stockVO = new StockVO(null,-1,-1,-1,-1,-1,-1,"","","");
        }
        return stockVO;
    }

    public CompareVO getCompare(String idtentification, Date beginDate, Date endDate) {
        CompareVO compareVO=new CompareVO("","",-1,-1,-1,null,null,-1);                    //代表用户输入时间内无数据
        String id=getStockId(idtentification);
        if(id!=null) {
            List<Stock> stocks = stockMapper.getOneStock(id, MathHelper.utilToSql(beginDate), MathHelper.utilToSql(endDate));
            if(stocks!=null && stocks.size()!=0) {
                double exBeginClose = -100000.0;                                                                 //通过多拿一个工作日的收盘价来计算第一天的收益率
                Date actualBeginDate = stocks.get(stocks.size() - 1).getDate();
                Date beforeStartCalendar = stockMapper.getExCalendar(id, MathHelper.utilToSql(actualBeginDate), 1);
                if (beforeStartCalendar != null) {
                    List<Stock> arrayList = stockMapper.getOneStock(id, MathHelper.utilToSql(beforeStartCalendar), MathHelper.utilToSql(beforeStartCalendar));
                    if(arrayList!=null)
                        exBeginClose = arrayList.get(0).getAdjClose();
                } else {
                    return compareVO;
                }
                if(exBeginClose!=-10000.0)
                    return calCompare(stocks, exBeginClose);
                else
                    return compareVO;
            }
            else{
                return compareVO;
            }
        }
        else{
            CompareVO compareVO2=new CompareVO("","",-2,-2,-2,null,null,-2);
            return compareVO2;
        }
    }

    public Market getMarketInfo(Date date) {
        Market market=stockMapper.getMarket(MathHelper.utilToSql(date));
        if(market!=null)
            return market;
        else{
            Market market1=new Market(-1,-1,-1,-1,-1,-1,-1);
            return market1;
        }
    }

    @Override
    public List<Market> getMarketInTime(Date dateStart, Date dateEnd){
        List<Market> list = stockMapper.getMarketInTime(MathHelper.utilToSql(dateStart),MathHelper.utilToSql(dateEnd));
        for(int i=0;i<list.size();i++){
            Market market=list.get(i);
            if(market.getDropStock()==0&&market.getHardenStock()==0)
                list.remove(i);
        }
        return list;
    }

    public List<Average> getAverageVO(String idtentification, Date beginDate, Date endDate) {
        Average average=new Average("",-1,-1,-1);
        List list=new ArrayList();
        String id=getStockId(idtentification);
        if(id!=null) {
            list = stockMapper.getAverageList(id, MathHelper.utilToSql(beginDate), MathHelper.utilToSql(endDate));
            if(list!=null)
                return list;
            else{
                list.add(average);
                return list;
            }
        }
        else{
            return list;
        }
    }

    public Map getIDList() {
        List<Map>mapList=stockMapper.getIDList();
        Map<String, String> result=new HashMap<>();
       for(int i=0;i<mapList.size();i++){
           Map tempMap=mapList.get(i);
           Iterator iterator = tempMap.entrySet().iterator();
           Map.Entry<String,String> entry = (java.util.Map.Entry)iterator.next();
           Object value = entry.getValue();
           entry = (java.util.Map.Entry)iterator.next();
           Object key = entry.getValue();
           result.put((String) key,(String) value);
       }
       return result;
    }

    @Override
    public List<StockVO> getCompleteStocks(String str) {
        Date date=new Date();
        List<Stock>stocks=new ArrayList<>();
        List<StockVO>stockVOs=new ArrayList<>();
        Boolean canSelect=stockMapper.hasProperStocks(str);
        if(canSelect) {
            stocks = stockMapper.getProperStocks(MathHelper.utilToSql(date), str);
            while (stocks.size()==0) {
                date = stockMapper.getExCalendar("", MathHelper.utilToSql(date), 1);
                stocks=stockMapper.getProperStocks(MathHelper.utilToSql(date),str);
            }
        }
        for(int i=0;i<stocks.size();i++){
            stockVOs.add(new StockVO(stocks.get(i)));
        }
        return stockVOs;
    }

    @Override
    public List<String> getAssociatingInput(String str){
        NameList nameList = NameList.getNameList();
        Map<String,String> map = nameList.getMap();
        Map<String,String> mapChange = nameList.getMapChange();
        List<String> ansList = new ArrayList<>();
        if(str.length() != 0){
            List<String> list = new ArrayList<>();
            list.addAll(stockMapper.getAssociatingInputCode(str));
            list.addAll(stockMapper.getAssociatingInputName(str));

            ansList = new ArrayList<>();
            for(String nameOrCode:list){
                String code  = map.get(nameOrCode);
                if(code != null){
                    ansList.add(code + "/" + nameOrCode);
                }else{
                    String name = mapChange.get(nameOrCode);
                    ansList.add(nameOrCode + "/" + name);
                }
            }
        }else{
            Set<String> set = mapChange.keySet();
            for(String line : set){
                ansList.add(line + "/" + mapChange.get(line));
            }
        }


        return ansList;
    }

    @Override
    public List<K_Market> getMarket(String market, Date dateStart,Date dateEnd) {
        List list=new ArrayList();
        String code = "";
        switch (market){
            case "上证指数":{
                code = "sh000001";
                break;
            }
            case "A股指数":{
                code = "sh000002";
                break;
            }
            case "综合指数":{
                code = "sh000008";
                break;
            }
        }
        list=stockMapper.getK_Market_More(code,MathHelper.utilToSql(dateStart),MathHelper.utilToSql(dateEnd));
        if(list==null){
            K_Market k_market=new K_Market(null,-1,-1,-1,-1,-1);
            list.add(k_market);
            return list;
        }
        else
            return list;
    }

    @Override
    public List<Dragon> getDragon(Date date) {
        return stockMapper.getDragon(MathHelper.utilToSql(date));
    }


    private CompareVO calCompare(List<Stock> stocks,double exBeginClose){
        Collections.reverse(stocks);
        int size=stocks.size();
        Stock first=stocks.get(0);
        String name=first.getName();
        String code=first.getCode();
        //计算涨跌幅
        double close2=stocks.get(size-1).getAdjClose();
        double close1=first.getAdjClose();
        double change=(close2-close1)/close1;

        double high=first.getHigh();
        double low=first.getLow();
        ArrayList<Double>closeList=new ArrayList<>(size);
        ArrayList<Double>logarithmicYield=new ArrayList<>(size);
        double logarithmicYieldVariance=0;   //同上

        double exClose=stocks.get(0).getAdjClose();
        double close=exClose;
        double totalLogarithmic=0;                                    //有一点担心：累加后超出double表示范围?!

        if(exBeginClose>0) {
            double firstLogarithmic = Math.log(exBeginClose / exClose);       //通过多拿一个工作日的数据得到第一天的对数收益率,如果得不到前一天的数据就将其对数收益率设为0
            logarithmicYieldVariance += firstLogarithmic;
            logarithmicYield.add(firstLogarithmic);
        }
        else{
            logarithmicYield.add(0.0);
        }

        for(int i=0;i<size;i++){
            Stock stockPO=stocks.get(i);
            if(stockPO.getLow()<low)
                low=stockPO.getLow();                //计算最高价
            if(stockPO.getHigh()>high)
                high=stockPO.getHigh();              //计算最低价
            exClose=close;
            close=stockPO.getAdjClose();
            closeList.add(stockPO.getClose());
            if(i!=0){
                double loagarithmic=Math.log(close/exClose);
                totalLogarithmic+=loagarithmic;
                logarithmicYield.add(loagarithmic);
            }
        }
        double average;
        if(exBeginClose<0)
            size--;
        average=totalLogarithmic/size;
        for(int i=0;i<size;i++){
            logarithmicYieldVariance+=Math.pow((logarithmicYield.get(i)-average),2);
        }
        CompareVO compareVO=new CompareVO(name,code,low,high,change,closeList,logarithmicYield,logarithmicYieldVariance);
        return compareVO;
    }


    private String getStockId(String idtentification){
        char first=idtentification.toCharArray()[0];
        if(first-'0'>=0&&'9'-first>=0)
            return idtentification;
        else
            return stockMapper.getStockId(idtentification);
    }


}
