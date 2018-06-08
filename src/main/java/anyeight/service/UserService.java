package anyeight.service;

import anyeight.model.AnswerPost;
import anyeight.model.Post;
import anyeight.model.UserInfo;
import anyeight.model.User_Collect;
import anyeight.vo.CustomStrategyVO;
import org.apache.commons.fileupload.FileUploadBase;

import java.util.List;

/**
 * Created by 啊 on 2017/5/25.
 */
public interface UserService {
    /*输入用户名和密码返回用户id，若不存在用户，返回null*/
    public String isLogin(String userName,String password);
    /*输入用户id返回用户信息*/
    public UserInfo getUserInfo(String id);
     /*添加新用户*/
     public Boolean addUser(String userName,String password,String email);
     /*修改用户邮箱*/
     public Boolean modifyEmail(String user_id,String email);
    /*修改用户头像*/
    public Boolean modifyGraph(String user_id,String graph) throws FileUploadBase.SizeLimitExceededException;
    /*修改用户电话*/
    public Boolean modifyPhone(String user_id,String phone);
    /*添加用户基本信息*/
    public Boolean addInfo(UserInfo userInfo);
    /*修改用户密码*/
    public Boolean modifyPassword(String id,String newPassword);
    /*输入用户id返回用户的收藏股票*/
    public User_Collect getUserCollectVO(String id);
    /*判断一个用户是否已经收藏了某只股票*/
    public boolean checkCollection(String user_id,String code);
    /*输入用户id返回用户的收藏策略*/
    public List<CustomStrategyVO> getUserStrategy(String id);
    /*新增用户收藏的股票*/
    public void addCollectStock(String user_id,String stock_id);
    /*删除用户收藏的股票*/
    public void delCollectStock(String user_id,String stock_id);
    /*新增用户收藏的策略*/
    public void addCollectStrategy(String user_id,CustomStrategyVO c);
    /*删除用户收藏的策略*/
    public void delCollectStrategy(String user_id,String strategyName);
    /*获取用户所有的发帖信息*/
    public List<Post> getUserPost(String user_id);
    /*获取用户的所有回帖*/
    public List<AnswerPost> getUserAnswerPost(String user_id);
}
