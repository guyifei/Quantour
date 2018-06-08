package anyeight.controller;

import anyeight.jsonTrans.jsonTrans;
import anyeight.model.UserInfo;
import anyeight.model.User_Collect;
import anyeight.service.CookieService;
import anyeight.service.UserService;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileUploadBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;



@Controller
public class UserController {
    private static final int DAY=24*60*60;
    @Autowired
    private UserService userService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private Tools tools;


    @RequestMapping(value = "/cookiecheck", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String checkCookie(HttpServletRequest request){
        Cookie[] c=request.getCookies();
        if (c == null) {
            return "_fail";
        }
        String id = tools.fetchId(c);
        if(id==null){
            return "_fail";
        }
        return new Gson().toJson(userService.getUserInfo(id));
    }

    @RequestMapping(value = "/user_login")
    @ResponseBody
    public String login(String username , String password, boolean iskeep, int keeplength, HttpServletRequest request, HttpServletResponse response){
        String user_id=userService.isLogin(username,password);
        if(user_id==null){
            return "fail";
        }
        UserInfo userInfo=userService.getUserInfo(user_id);
        String result=new Gson().toJson(userInfo);
        String forcode=username+String.valueOf(Math.random());
        String code="";
        try {
            code=new BASE64Encoder().encode(forcode.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /*
          cookie添加到数据库
        */

        Cookie temp=new Cookie("logincode",code);
        if(iskeep){
            temp.setMaxAge(keeplength*DAY);
        }else{
            keeplength=-1;
        }
        cookieService.addCookie(user_id,code,keeplength);
        response.addCookie(temp);
        return result;
    }


    @RequestMapping(value = "/add_user")
    @ResponseBody
    public String addUser(String username , String password, String email, HttpServletRequest request, HttpServletResponse response){
        Boolean result=userService.addUser(username,password,email);
        if(!result){
            return "fail";
        }else{
            return "success";
        }
    }


    @RequestMapping(value = "/user_logout")
    public String logout(HttpServletRequest request){
        /***
         * 不知道该怎么实现
        request.getSession().removeAttribute("Username");
        request.getSession().removeAttribute("Type");
        request.getSession().removeAttribute("MemberMessageVO");
        return "login";
         ***/
        return null;
    }


    @RequestMapping(value = "/user_collect")
    @ResponseBody
    public String getUserCollect(String user_id){
        User_Collect user_collect=userService.getUserCollectVO(user_id);
        jsonTrans trans=new jsonTrans();
        String result=trans.collectInfoTrans(user_collect);
        return result;
    }

    @RequestMapping(value = "/userinfo")
    @ResponseBody
    public String modifyUserInfo(HttpServletRequest request, String tel, String email){
        Cookie[] cookies=request.getCookies();
        String id = tools.fetchId(cookies);
        if(id==null)
            return "fail";

        Boolean result=userService.modifyEmail(id, email);
        result = result && (userService.modifyPhone(id, tel));
        if(result)
            return "success";
        else
            return "fail";
    }

    @RequestMapping("/modify_avatar")
    @ResponseBody
    public String modifyAvatar(MultipartFile file, HttpServletRequest request){
        System.out.println("开始");
        String id = tools.fetchId(request.getCookies());
        String path = request.getSession().getServletContext().getRealPath("\\")+"\\data\\avatar\\"+id;
        String fileName = file.getOriginalFilename();

        System.out.println(path);
        fileName="ava"+fileName.substring(fileName.length()-4);
        File targetFile = new File(path, fileName);
        if(targetFile.exists()){
//            targetFile.mkdirs();
            fileName="r"+fileName;
            targetFile=new File(path, fileName);
        }else{
            targetFile.mkdirs();
        }
        for(File f:targetFile.getParentFile().listFiles()){
            f.delete();
        }
        targetFile.mkdirs();

        //保存
        String pathForSql = "../data/avatar/" + id + "/" + fileName;
        try {
            file.transferTo(targetFile);
            userService.modifyGraph(id,pathForSql);
        } catch (FileUploadBase.SizeLimitExceededException a){
            return "sizeOverflow";
        }catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }

        return pathForSql;
    }
}
