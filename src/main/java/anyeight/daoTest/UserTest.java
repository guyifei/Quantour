package anyeight.daoTest;

import anyeight.dao.UserMapper;
import anyeight.model.UserInfo;
import anyeight.model.User_Collect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

/**
 * Created by T5-SK on 2017/5/29.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class UserTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testIsLogin(){
        String ans = userMapper.isLogin("lalalal","hahaha");
        System.out.println(ans);
    }

    @Test
    public void testAddUser(){
        userMapper.addUser("adasdasd","hahaha","4564564");
    }

    @Test
    public void testGetUserInfo(){
        UserInfo userInfo = userMapper.getUserInfo("100001");
        System.out.println("success");
    }



    @Test
    public void testModifyInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUser_id("17685687");
        userInfo.setUserName("withoutbrain");
        userInfo.setTelephone("13270898633");
        userInfo.setEmail("81433596@qq.com");
        userInfo.setGraph("lalal");
        boolean result = userMapper.modifyInfo(userInfo);
        System.out.println(result);
    }

    @Test
    public void testModifyPassword(){
        boolean result = userMapper.modifyPassword("100003", "what happened");
        System.out.println(result);
    }

    @Test
    public void testgGtUserCollectVO(){
        User_Collect user_collect = userMapper.getUserCollectVO("000002");
        System.out.println(user_collect.getCollectStockName().size());
    }

    @Test
    public void testAddUserCollectVO(){
        userMapper.addCollectStock("000002","0003131");
    }

    @Test
    public void testAddCookie(){
        Date date = Date.valueOf("2017-6-1");
        userMapper.addCookie("100002","hkdsfhalsfh",date,9);
    }



}
