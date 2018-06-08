package anyeight.dao;

import anyeight.model.UserInfo;
import anyeight.model.User_Collect;
import anyeight.vo.CustomStrategyVO;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 啊 on 2017/5/29.
 */
@Repository
public interface UserMapper {
    /*输入用户名和密码返回用户id，若不存在用户，返回null*/
    public String isLogin(String userName,String password);
    /*输入用户id返回用户信息*/
    public UserInfo getUserInfo(String id);
    /*输入用户id返回用户收藏股票*/
    public User_Collect getUserCollectVO(String id);
    /*输入用户id返回用户收藏策略*/
    public List<CustomStrategyVO> getCollectStrategy(String id);
    /*添加新用户，返回添加结果，要求username不存在*/
    public void addUser(String userName,String password,String email);
    /*查询是否已经有这个user_name的用户，如果已经存在返回true*/
    public Boolean checkUserName(String userName);
    /*修改用户信息，返回是否修改成功（应在添加用户后自动先让用户补全用户信息）*/
    public Boolean modifyInfo(UserInfo userInfo);
    /*修改用户密码*/
    public Boolean modifyPassword(String id,String newPassword);
    /*根据cookie里的密文返回用户ID*/
    public String getIdByCookie(String ciphertext);
    /*添加cookie，如果cookie已经存在就用当前输入的时间和持有时间替换*/
    public void addCookie(String user_id,String ciphertext, Date beginDate,int holdDays);
    /*判断一个用户的是否收藏过某只股票*/
    public boolean checkUserCollection(String user_id,String code);
    /*新增用户收藏的股票*/
    public void addCollectStock(String user_id,String stock_id);
    /*删除用户收藏的股票*/
    public void delCollectStock(String user_id,String stock_id);
    /*新增用户收藏的策略*/
    public void addCollectStrategy(String user_id,CustomStrategyVO customStrategyVO);
    /*删除用户收藏的策略*/
    public void delCollectStrategy(String user_id,String strategyName);
    /*返回所有用户id和email*/
    public Map getCustomerEmail();

}
