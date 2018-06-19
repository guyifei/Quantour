package anyeight.controller;

import anyeight.jsonTrans.jsonTrans;
import anyeight.model.K_Market;
import anyeight.service.NeuralLearn;
import anyeight.service.StockService;
import anyeight.service.UserService;
import anyeight.vo.CompareVO;
import anyeight.vo.StockVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */
@Controller
@RequestMapping("/stockFunc")
public class StockContoller {
    @Qualifier("stockServiceImpl")
    @Autowired
    private StockService stockService;
    @Autowired
    private Tools tools;
    @Autowired
    private UserService userService;
    @Autowired
    private NeuralLearn neuralLearn;


    @RequestMapping("stock")
    @ResponseBody
    public String getStock(String stockname,String startDate,String endDate) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date end=format.parse(endDate);
        Date start=format.parse(startDate);
        List<StockVO> stocks=stockService.getStockInfo(stockname,start,end);
        jsonTrans trans=new jsonTrans();
        String result=trans.stockTrans(stocks);
        return result;
    }
    @RequestMapping("board")
    @ResponseBody
    public String getBoard(String boardname) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate=format.parse("2018-06-10");
        Date startDate=format.parse("2018-03-01");
        List<K_Market> market=stockService.getMarket(boardname,startDate,currentDate);
        jsonTrans trans=new jsonTrans();
        String result=trans.marketTrans(market);
        return result;
    }

    @RequestMapping(value = "search", produces = "text/html;charset=UTF-8")
    public ModelAndView search(HttpServletRequest request,String key, HttpServletResponse response) {
        if(key.length()==0){
            response.setHeader("refresh","0;/stocklist");
            return new ModelAndView("stocklist");
        }
        if(key.split("/").length==2){
            key=key.split("/")[0];
        }
        if(key.split("_").length==2){
            key=key.split("/")[0];
        }
        ModelAndView result=new ModelAndView("stocklist");
        List<StockVO> list = stockService.getCompleteStocks(key);
        List<Boolean> booleanList=new ArrayList();

        String userId = tools.fetchId(request.getCookies());
        if(userId==null){
            for(int i=0;i<list.size();i++) {
                booleanList.add(false);
            }
        }else{
            for (StockVO aList : list) {
                booleanList.add(userService.checkCollection(userId, aList.getCode()));
            }
        }
        result.addObject("favorList", booleanList);
        result.addObject("list", list);
        return result;
    }

    @RequestMapping(value="/stockInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getStockInfo(String stockname,String endDate) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date end=format.parse(endDate);
        Date start=format.parse(getSpecifiedDayBefore(endDate));
        List<StockVO> stocks=stockService.getStockInfo(stockname,start,end);
        StockVO vo=stocks.get(0);
        return new Gson().toJson(vo);
    }

    @RequestMapping("compare")
    @ResponseBody
    public String getCompare(String stockname,String startDate,String endDate) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date end=format.parse(endDate);
        Date start=format.parse(startDate);
        stockname = stockname.split("/")[0];
        List<StockVO> stocks=stockService.getStockInfo(stockname,start,end);
        jsonTrans trans=new jsonTrans();
        String result=trans.stockCompare(stocks);
        return result;
    }

    @RequestMapping(value="/compareInfo",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getCompareInfo(String stockname,String startDate,String endDate) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date end=format.parse(endDate);
        Date start=format.parse(startDate);
        stockname = stockname.split("/")[0];
        CompareVO compare=stockService.getCompare(stockname,start,end);
        return new Gson().toJson(compare);
    }


    public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 3);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    @RequestMapping(value = "addFavor", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String addFavor(HttpServletRequest request, HttpServletResponse response, String stockId) {
        String userId = tools.fetchId(request.getCookies());
        if(userId==null){
            response.setHeader("refresh","0;/login");
            return "没有登录";
        }
        userService.addCollectStock(userId, stockId);
        return "success";
    }

    @RequestMapping("removeFavor")
    @ResponseBody
    public String removeFavor(HttpServletRequest request, HttpServletResponse response, String stockId) {
        String userId = tools.fetchId(request.getCookies());
        if(userId==null){
            response.setHeader("refresh","0;/login");
            return "没有登录";
        }
        userService.delCollectStock(userId, stockId);
        return "success";
    }
    @RequestMapping("checkFavor")
    @ResponseBody
    public boolean checkFavor(HttpServletRequest request, String stockId){
        String userId=tools.fetchId(request.getCookies());
        if(userId==null){
            return false;
        }
        boolean re =  userService.checkCollection(userId, stockId);
        return re;
    }


    @RequestMapping(value="/neural",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getNeural(String stockname,String startDate,String midDate,String endDate) throws ParseException {
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        Date end=format.parse(endDate);
        Date mid=format.parse(midDate);
        Date start=format.parse(startDate);
        List<ArrayList<Double>> resultList=neuralLearn.NeuralLearn(stockname,start,mid,end);
        if(resultList.size()==0){
            return new Gson().toJson("wrong");
        }
        List<ArrayList<Double>> earn=neuralLearn.MoneyGet(resultList);
        jsonTrans trans=new jsonTrans();
        String result=trans.neuralTrans(resultList,earn);
        return result;
    }
}
