package anyeight.controller;

import anyeight.jsonTrans.jsonTrans;
import anyeight.model.Dragon;
import anyeight.model.Market;
import anyeight.service.HomeService;
import anyeight.service.StockService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by 35930 on 2017/5/15.
 */
@Controller
@RequestMapping("/function")
public class FunctionController {
    @Autowired
    private StockService stockService;
    @Autowired
    private HomeService homeService;

    @RequestMapping("market")
    @ResponseBody
    public String fetchMarket(String date){
        System.out.println(date);
        Date d=Date.valueOf(date);
        Market market=stockService.getMarketInfo(d);
        if(market==null)
            return new Gson().toJson("empty");
        System.out.println(market.getTotalVolume());
        return new Gson().toJson(market);
    }

    @RequestMapping("hot")
    @ResponseBody
    public String fetchHot(String startDate,String endDate){
        Date start=Date.valueOf(startDate);
        Date end=Date.valueOf(endDate);
        List<Market> markets=stockService.getMarketInTime(start,end);
        if(markets==null)
            return new Gson().toJson("empty");
        jsonTrans jsonTrans=new jsonTrans();
        return jsonTrans.maketLine(markets);
    }

    @RequestMapping(value="/rank",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fetchRank(String date){
        Date d=Date.valueOf(date);
        List<Dragon> dragons=stockService.getDragon(d);
        if(dragons==null)
            return new Gson().toJson("empty");
        return new Gson().toJson(dragons);
    }

    @RequestMapping("fetchMod")
    @ResponseBody
    public String fetchMod(){
//        homeService.g
        return "fail";
    }

    @RequestMapping(value = "getList", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getList(String key){
        if(key==null|| Objects.equals(key, " ")||Objects.equals(key,"")){
            return new Gson().toJson("space");
        }

        List<String> result=stockService.getAssociatingInput(key);
//        String[] s = {"123", "234", "456", "567", "678"};
        return new Gson().toJson(result);
    }
}
