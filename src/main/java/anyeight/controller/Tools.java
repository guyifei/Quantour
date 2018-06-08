package anyeight.controller;

import anyeight.service.CookieService;
import anyeight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;

/**
 * Created by 35930 on 2017/6/8.
 */
@Controller
public class Tools {
    @Autowired
    private CookieService cookieService;
    @Autowired
    private UserService userService;

    private static UserService staticUserService;

    private static boolean staticInitiated=false;

    public Tools(){
        super();
        init();
    }

    private void init(){
        staticUserService=userService;

        staticInitiated=true;
    }
    public String fetchId(Cookie[] cookies){
        String id;
        if (cookies == null) {
            return null;
        }
        for(Cookie c:cookies){
            if(c.getName().equals("logincode")){
                id = cookieService.getIdByCookie(c.getValue());
                if (id == null) {
                    return null;
                }else{
                    return id;
                }
            }
        }
        return null;
    }
    public static boolean checkFavor(String userId, String stockCode){
        if(!staticInitiated)
            return false;
        return staticUserService.checkCollection(userId, stockCode);
    }
}
