package anyeight.serviceImpl;

import anyeight.dao.ForumMapper;
import anyeight.dao.UserMapper;
import anyeight.model.AnswerPost;
import anyeight.model.Post;
import anyeight.model.UserInfo;
import anyeight.model.User_Collect;
import anyeight.service.UserService;
import anyeight.util.SendMail;
import anyeight.vo.CustomStrategyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 啊 on 2017/5/29.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    ForumMapper forumMapper;

    @Override
    public String isLogin(String userName, String password) {
        String str= userMapper.isLogin(userName,password);
        return str;
    }

    @Override
    public UserInfo getUserInfo(String id) {
        UserInfo userInfo=userMapper.getUserInfo(id);
        if(userInfo!=null)
            return userInfo;
        else{
            UserInfo userInfo1=new UserInfo("","","","","");
            return userInfo1;
        }
    }


    @Override
    public List<CustomStrategyVO> getUserStrategy(String id) {
        List list=userMapper.getCollectStrategy(id);
        if(list!=null)
            return list;
        else{
            list=new ArrayList();
            return list;
        }
    }

    @Override
    public void addCollectStock(String user_id, String stock_id) {
       userMapper.addCollectStock(user_id,stock_id);
    }

    @Override
    public void delCollectStock(String user_id, String stock_id) {
        userMapper.delCollectStock(user_id, stock_id);
    }

    @Override
    public void addCollectStrategy(String user_id, CustomStrategyVO customStrategyVO) {
       userMapper.addCollectStrategy(user_id, customStrategyVO);
    }

    @Override
    public void delCollectStrategy(String user_id, String strategyName) {
       userMapper.delCollectStrategy(user_id, strategyName);
    }

    @Override
    public List<Post> getUserPost(String user_id) {
        return forumMapper.getUserPost(user_id);
    }

    @Override
    public List<AnswerPost> getUserAnswerPost(String user_id) {
        return forumMapper.getUserAnswerPost(user_id);
    }


    @Override
    public Boolean addUser(String userName, String password,String email) {
       if(!userMapper.checkUserName(userName)) {
           if (isValidInput(userName)) {
               userMapper.addUser(userName,password,email);
               SendEmail sendEmail = new SendEmail(email, "欢迎注册AnyEight股票系统");
               Thread thread = new Thread(sendEmail);
               thread.start();
               return true;
           }else
               return false;
       }else
            return false;
    }

    @Override
    public Boolean modifyEmail(String user_id,String email) {
        UserInfo userInfo=userMapper.getUserInfo(user_id);
        userInfo.setEmail(email);
        return userMapper.modifyInfo(userInfo);
    }

    @Override
    public Boolean modifyGraph(String user_id,String graph) {
        UserInfo userInfo=userMapper.getUserInfo(user_id);
        userInfo.setGraph(graph);
        return userMapper.modifyInfo(userInfo);
    }

    @Override
    public Boolean modifyPhone(String user_id,String phone) {
        UserInfo userInfo=userMapper.getUserInfo(user_id);
        userInfo.setTelephone(phone);
        return userMapper.modifyInfo(userInfo);
    }

    @Override
    public Boolean addInfo(UserInfo userInfo) {
        return userMapper.modifyInfo(userInfo);
    }



    @Override
    public Boolean modifyPassword(String id, String newPassword) {
        return userMapper.modifyPassword(id, newPassword);
    }



    @Override
    public boolean checkCollection(String user_id,String code){
        return userMapper.checkUserCollection(user_id,code);
    }

    @Override
    public User_Collect getUserCollectVO(String id) {
       User_Collect user_collect=userMapper.getUserCollectVO(id);
        if(user_collect!=null)
            return user_collect;
        else{
            User_Collect user_collect1=new User_Collect("",null,null,null,null);
            return user_collect1;
        }
    }

    private Boolean isValidInput(String str){
        int length=str.length();
        if(length<6||length>16)
            return false;
        char[] array=str.toCharArray();
        for(int i=0;i<length;i++){
            char c=array[i];
            if((c>=48&&c<=57)||(c>=97&&c<=122)||(c>=65&&c<=90));
            else
                return false;
        }
        return true;
    }
}
