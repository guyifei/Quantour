package anyeight.controller;

import anyeight.service.CookieService;
import anyeight.service.ForumService;
import anyeight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by 35930 on 2017/6/8.
 */

@Controller
@RequestMapping("/passageFunc")
public class PassageFunc {
    @Autowired
    private ForumService forumService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private UserService userService;
    @Autowired
    private Tools tools;


    @RequestMapping("publish")
    @ResponseBody
    public String publish(HttpServletRequest request, HttpServletResponse response, String title, String content, String tag, boolean nicked){
        String id = tools.fetchId(request.getCookies());
        if(id==null){
            response.setHeader("refresh", "0;/login");
            return "没有登录";
        }
        System.out.println(nicked);
        String passage_id=forumService.addPost(id, new Date(), content, title);

        ModelAndView modelAndView = new ModelAndView("passage");

        response.setHeader("refresh","0;/passage/"+passage_id);
        return "文章发表成功";
    }

    @RequestMapping(value = "comment",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String comment(HttpServletResponse response, HttpServletRequest request, String comment, String passage_id){
        String user_id = tools.fetchId(request.getCookies());
        if (user_id == null) {
            response.setHeader("refresh", "0;/login");
            return "没有登录";
        }
        forumService.addAnswer(passage_id, user_id, new Date(), comment);
        response.setHeader("refresh","0;/passage/"+passage_id);
        return "评论成功";
    }

}
