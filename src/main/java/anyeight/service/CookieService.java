package anyeight.service;

/**
 * Created by 啊 on 2017/6/1.
 */
public interface CookieService {
    /*根据cookie里的密文返回用户ID*/
    public String getIdByCookie(String ciphertext);
    /*添加cookie，如果cookie已经存在就用当前输入的时间和持有时间替换*/
    public void addCookie(String user_id, String ciphertext, int holdDays);
}
