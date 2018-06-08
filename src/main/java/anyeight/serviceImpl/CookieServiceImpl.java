package anyeight.serviceImpl;

import anyeight.dao.UserMapper;
import anyeight.service.CookieService;
import anyeight.service.UserService;
import anyeight.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * Created by 啊 on 2017/6/1.
 */
@Service
public class CookieServiceImpl implements CookieService{
    @Autowired
    UserMapper userMapper;
    @Override
    public String getIdByCookie(String ciphertext) {
        String str=userMapper.getIdByCookie(ciphertext);
        if(str!=null)
            return str;
        else
            return "";
    }

    @Override
    public void addCookie(String user_id, String ciphertext,int holdDays) {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间  
        userMapper.addCookie(user_id,ciphertext, MathHelper.utilToSql(curDate),holdDays);
    }
}
