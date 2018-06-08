package anyeight.serviceImpl;

import anyeight.dao.ForumMapper;
import anyeight.dao.StockMapper;
import anyeight.model.K_Market;
import anyeight.model.News;
import anyeight.model.Stock;
import anyeight.service.HomeService;
import anyeight.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 啊 on 2017/5/18.
 */
@Service
public class HomeServiceImpl implements HomeService{
    @Autowired
    StockMapper stockMapper;
    @Autowired
    ForumMapper forumMapper;

    @Override
    public List<Stock> getHottestStock(Date date,String plate) {
        List list=new ArrayList();
        Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
        if(date1!=null)
            return  stockMapper.getHottestStock(MathHelper.utilToSql(date1),plate);
        else
            return list;
    }

    @Override
    public K_Market getK_Market(Date date) {
        Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
        if(date1!=null) {
            K_Market k_market = stockMapper.getK_Market(MathHelper.utilToSql(date1));
            if(k_market!=null)
            return k_market;
        }
        K_Market k_market=new K_Market(null,-1,-1,-1,-1,-1);
        return k_market;
    }

    @Override
    public List<String> getHottestMarket(Date date) {
        Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
        if(date1!=null) {
            Map<String, String> map = stockMapper.getHottestMarket(MathHelper.utilToSql(date1));
            if(map.size()>=3) {
                List<String> list = new ArrayList<>();
                list.add(map.get("first"));
                list.add(map.get("second"));
                list.add(map.get("third"));
                list.add(map.get("fourth"));
                list.add(map.get("fifth"));
                list.add(map.get("sixth"));
                list.add(map.get("seventh"));
                list.add(map.get("eighth"));
                list.add(map.get("ninth"));
                return list;
            }
        }
        List list = new ArrayList();
        return list;
    }

    @Override
    public List<News> getNewsInstantNews(){
        return forumMapper.getNewsInstantNews();
    }

//    @Override
//    public List<String> getHottestMarket(Date date) {
//        Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
//        List<String> marketList = stockMapper.getBlockNameList();
//        //map对应版块内的最热门的三只股票
//        Map<String,List> map = new HashMap<>();
//        for(int i = 0; i < marketList.size() ; i++){
//            map.put(marketList.get(i),stockMapper.getHottestStock(MathHelper.utilToSql(date1),marketList.get(i)));
//        }
//        Set<String> set = map.keySet();
//        List<Stock> list = null;
//        Map<Double,String> mapForList = new TreeMap();
//        double ans = 0;
//        double i = 0;
//        for(String str : set){
//            list = map.get(str);
//            for(Stock stock : list){
//                ans += stock.getVolome();
//                i++;
//            }
//            ans = ans/i;
//            mapForList.put(ans,str);
//            ans = 0;
//            i = 0;
//        }
//
//        List<String> ansList = new ArrayList<>();
//        Set<Double> set1 = mapForList.keySet();
//        int x = set1.size();
//        for (Double number:set1){
//            if(x<4){
//                ansList.add(mapForList.get(number));
//            }else {
//                x--;
//            }
//        }
//        //第三大第二大第一大，倒着排序
//        return ansList;
//    }

//    @Override
//    public List<String> getHottestMarket(Date date) {
//        Date date1 = stockMapper.getExCalendar("",MathHelper.utilToSql(date),1);
//        List<String> marketList = stockMapper.getBlockNameList();
//        Map<String,List> map = new HashMap<>();
//        for(int i = 0; i < marketList.size(); i++){
//            map.put(marketList.get(i),stockMapper.getStocksByPlate(marketList.get(i)));
//        }
//        Set<String> set = map.keySet();
//        List<String> list = new ArrayList<>();
//        List<Stock> stockList =  stockMapper.getOneDayStock(MathHelper.utilToSql(date1));
//        List<Stock> listForSum = new ArrayList<>();
//        Map<Double,String> mapForList = new TreeMap();
//        for (String str : set){
//            list = map.get(str);
//            for (int m=0; m<stockList.size();m++){
//                if(list.contains(stockList.get(m).getCode())){
//                    listForSum.add(stockList.get(m));
//                }
//            }
//            double ans = 0;
//            for (int n=0; n<listForSum.size();n++){
//                ans += listForSum.get(n).getVolome();
//            }
//            ans = ans/listForSum.size();
//            mapForList.put(ans,str);
//            listForSum.clear();
//        }
//
//        List<String> ansList = new ArrayList<>();
//        Set<Double> set1 = mapForList.keySet();
//        int x = set1.size();
//        for (Double number:set1){
//            if(x<4){
//                ansList.add(mapForList.get(number));
//            }else {
//                x--;
//            }
//        }
////        ansList.add(mapForList.get(mapForList.get(0)));
////        ansList.add(mapForList.get(mapForList.get(1)));
////        ansList.add(mapForList.get(mapForList.get(2)));
//        return ansList;
//    }

}
