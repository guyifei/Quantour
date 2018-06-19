package anyeight.controller;

import anyeight.jsonTrans.jsonTrans;
import anyeight.model.*;
import anyeight.service.*;
import anyeight.vo.StockVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by ALIENWARE-PC on 2017/5/12.
 */
@Controller
public class MainController {
    @Autowired
    private
    CookieService cookieService;
    @Autowired
    private
    UserService userService;
    @Autowired
    private
    StockService stockService;
    @Autowired
    private
    HomeService homeService;
    @Autowired
    private
    ForumService forumService;
    @Autowired
    private
    Tools tools;

    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView result=new ModelAndView("index");
        List<String> marketlist=homeService.getHottestMarket(new Date());
        if(marketlist.size() == 0){
            result.addObject("msg","date is wrong");
        }

        List<Stock>[] list=new List[3];
        for(int i=0;i<3;i++){
            list[i]=homeService.getHottestStock(new Date(), marketlist.get(i));
        }
        List<News> newsList=homeService.getNewsInstantNews();
        List<Boolean>[] favorList=new List[3];
        String userId = tools.fetchId(request.getCookies());
        for(int i=0;i<3;i++){
            favorList[i] = new ArrayList<>();
            for(int j=0;j<3;j++){
                if(userId!=null){
                    favorList[i].add(userService.checkCollection(userId, list[i].get(j).getCode()));
                }else{
                    favorList[i].add(false);
                }

            }
        }
        result.addObject("stockList", list);
        result.addObject("marketList", marketlist);
        result.addObject("newsList", newsList);
        result.addObject("favorList", favorList);
        return result;
    }


    @RequestMapping("analyze")
    public String analyze(){
        return "analyze";
    }


    @RequestMapping(value = "stock")
    public ModelAndView stock(String stockname, HttpServletResponse response, HttpServletRequest request) throws IOException {

        ModelAndView modelAndView = new ModelAndView("stock");
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date start= null;
        Date end=null;
        try {
            start = df.parse("2018-03-01");
            end=df.parse("2018-06-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<StockVO> stocks=stockService.getStockInfo(stockname,start,end);
        if (stocks==null||stocks.size() == 0) {
            response.sendError(404);
            return modelAndView;
        }
        jsonTrans trans=new jsonTrans();
        String re = new Gson().toJson(stocks);
        String test = trans.stockTrans(stocks);
        boolean favor;
        String userId = tools.fetchId(request.getCookies());
        if (userId == null) {
            favor=false;
        }else{
            favor = userService.checkCollection(userId, stocks.get(0).getCode());
        }
//        stockService.getRecentlyStock(stocks.ge)
        StockVO stockinfo = stockService.getRecentlyStock(stockname, new Date());
        modelAndView.addObject("stockinfo", stockinfo);
        modelAndView.addObject("newsList", forumService.getNews(stocks.get(0).getCode()));
        modelAndView.addObject("stockname", stocks.get(0).getName());
        modelAndView.addObject("stockId", stocks.get(0).getCode());
        modelAndView.addObject("recentPrice",stocks.get(0).getClose());
        modelAndView.addObject("rise", stocks.get(0).getClose() > stocks.get(0).getOpen());
        modelAndView.addObject("changeRatio",(stocks.get(0).getClose()-stocks.get(0).getOpen())/stocks.get(0).getOpen());
        modelAndView.addObject("favor", favor);
        modelAndView.addObject("dataStr", test);
        return modelAndView;
    }
    @RequestMapping(value="profile", produces="text/html;charset=UTF-8")
    public ModelAndView stock(HttpServletRequest request){
        ModelAndView result = new ModelAndView("profile");
        String userId = tools.fetchId(request.getCookies());
        if (userId == null) {
            return new ModelAndView("login");
        }
        UserInfo userInfo = userService.getUserInfo(userId);
        String temp = new Gson().toJson(userInfo);
        User_Collect uc=userService.getUserCollectVO(userId);
        List<String> namelist = new ArrayList<>();
        List<Post> postList = userService.getUserPost(userId);
        List<AnswerPost> answerPosts = userService.getUserAnswerPost(userId);
        result.addObject("result", userInfo);
        result.addObject("postList", postList);
        result.addObject("answerList", answerPosts);
        result.addObject("favorites", uc.getCollectStockId());
        result.addObject("favoritesName",uc.getCollectStockName());
        return result;
    }

    @RequestMapping("stocklist")
    public ModelAndView stocklist(){
        ModelAndView modelAndView = new ModelAndView("stocklist");
        List<String> list=homeService.getHottestMarket(new Date());
        modelAndView.addObject("marketList", list);
        return modelAndView;
    }

    @RequestMapping("compare")
    public String compare(){
        return "compare";
    }
    @RequestMapping("login")
    public String login(){
        return "login";
    }
    @RequestMapping("regist")
    public String regist(){
        return "regist";
    }

    @RequestMapping("market")
    public String market(){
        return "market";
    }

    @RequestMapping(value = "backCompute")
    public ModelAndView backCompute(String startDate, String endDate,int form,int hold,int optionsRadios,int plateNumber,String[] nodes,int strategyNumber,
                                    int relationNumber,String relationForm,String relationHold){
        ModelAndView modelAndView = new ModelAndView("backCompute");
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate",endDate);
        modelAndView.addObject("form",form);
        modelAndView.addObject("hold",hold);
        modelAndView.addObject("optionsRadios",optionsRadios);
        modelAndView.addObject("plateNumber",plateNumber);
        modelAndView.addObject("nodes",new Gson().toJson(nodes));
        modelAndView.addObject("strategyNumber",strategyNumber);
        modelAndView.addObject("relationNumber",relationNumber);
        modelAndView.addObject("relationForm",relationForm);
        modelAndView.addObject("relationHold",relationHold);
        return modelAndView;
    }

    @RequestMapping(value = "strategyCompute")
    public ModelAndView strategyCompute(String startDate, String endDate,int form,int hold,int optionsRadios,
                                        int plateNumber,String[] nodes,int[] filterNames, String[] filterSelects,
                                        String[] filterValues,int[] rankNames,String[] rankSelects){
        ModelAndView modelAndView = new ModelAndView("strategyCompute");
        modelAndView.addObject("startDate", startDate);
        modelAndView.addObject("endDate",endDate);
        modelAndView.addObject("form",form);
        modelAndView.addObject("hold",hold);
        modelAndView.addObject("optionsRadios",optionsRadios);
        modelAndView.addObject("plateNumber",plateNumber);
        modelAndView.addObject("nodes",new Gson().toJson(nodes));
        modelAndView.addObject("filterNames",new Gson().toJson(filterNames));
        modelAndView.addObject("filterSelects",new Gson().toJson(filterSelects));
        modelAndView.addObject("filterValues",new Gson().toJson(filterValues));
        modelAndView.addObject("rankNames",new Gson().toJson(rankNames));
        modelAndView.addObject("rankSelects",new Gson().toJson(rankSelects));
        return modelAndView;
    }

//    @RequestMapping("mean")
//    public String mean(){
//        return "mean";
//    }

    @RequestMapping("momentum")
    public String momentum(){
        return "momentum";
    }

    @RequestMapping("bbs")
    public ModelAndView bbs() {
        ModelAndView modelAndView = new ModelAndView("bbs");
        List<Post> list=forumService.getPostList();
        modelAndView.addObject("passageList", list);
        return modelAndView;
    }
    @RequestMapping("passage/{passage_id}")
    public ModelAndView fetchPassage(@PathVariable("passage_id")String passage_id, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ModelAndView modelAndView = new ModelAndView("passage");
        //TODO 根据帖子ID取帖子信息的实现
        Post post = forumService.getOnePost(passage_id);
        if(post==null){
            response.sendError(404);
            return modelAndView;
        }
        List<AnswerPost> answerPosts=forumService.getAnswerPost(passage_id);
        List<UserInfo> answerUsers = new ArrayList<>();
        for (AnswerPost answer : answerPosts) {
            answerUsers.add(userService.getUserInfo(answer.getUser_id()));
        }
        boolean islogged=tools.fetchId(request.getCookies())!=null;
        modelAndView.addObject("post", post);
        modelAndView.addObject("author", userService.getUserInfo(post.getUser_id()));
        modelAndView.addObject("answers", answerPosts);
        modelAndView.addObject("islogged", islogged);
        modelAndView.addObject("answerAuthors",answerUsers);
        return modelAndView;
    }

    private ModelAndView checkLogin(ModelAndView result, Cookie[] c) {
        if (c == null) {
            return new ModelAndView("login");
        }
        for (Cookie aC : c) {
            if (Objects.equals(aC.getName(), "logincode")) {
                String id=cookieService.getIdByCookie(aC.getValue());
                if(id==null){
                    return new ModelAndView("login");
                }

                UserInfo temp = userService.getUserInfo(id);
                String json=new Gson().toJson(temp);
                result.addObject("result", temp);
                return result;
            }

        }
        return new ModelAndView("login");
    }
    @RequestMapping("newpassage")
    public ModelAndView newPassage(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView("newpassage");
        return checkLogin(modelAndView, request.getCookies());
    }

    @RequestMapping("strategy")
    public String strategy(){
        return "strategy";
    }

    @RequestMapping("neuralLearn")
    public String neuralLearn(){
        return "neuralLearn";
    }
}
