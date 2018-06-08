package anyeight.model;

import java.sql.Date;

/**
 * Created by 啊 on 2017/6/3.
 */
public class Post {
    /**
     * 帖子ID
     */
    private String post_id;
    /**
     * 用户ID
     */
    private String user_id;
    /**
     * 用户名字
     */
    private String user_name;
    /**
     * 用户发帖时间
     */
    private Date date;
    //文章里面存的是相对路径
    /**
     * 帖子内容
     */
    private String text;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 热度，按回帖数计算
     */
    private  int hotPoint=0;
    /**
     * 回帖数
     */
    private int current_size=0;
    public Post(){

    }
    public Post(String post_id,String user_id,Date date,String text,String title,int hotPoint,int current_size){
        this.post_id=post_id;
        this.user_id=user_id;
        this.date=date;
        this.text=text;
        this.title=title;
        this.hotPoint=hotPoint;
        this.current_size=current_size;
    }

    public int getHotPoint() {
        return hotPoint;
    }

    public void setHotPoint(int hotPoint){
        this.hotPoint=hotPoint;
    }

    public void setCurrent_size(int current_size) {
        this.current_size = current_size;
    }

    public int getCurrent_size() {
        return current_size;
    }

    public String getUser_id() {
        return user_id;
    }


    public String getPost_id() {
        return post_id;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }
}
