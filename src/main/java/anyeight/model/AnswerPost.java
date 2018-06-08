package anyeight.model;

import java.sql.Date;

/**
 * Created by 啊 on 2017/6/3.
 */
public class AnswerPost {
    /**
     * 回复的原贴ID
     */
    private String origin_id;
    /**
     * 回复帖子的ID
     */
    private String answer_id;
    /**
     * 发帖人的ID
     */
    private String user_id;
    /**
     * 发帖时间
     */
    private Date date;
    /**
     * 帖子内容
     */
    private String text;
    /**
     * 楼层
     */
    private int floor;

    public String getUser_id() {
        return user_id;
    }

    public String getOrigin_id() {
        return origin_id;
    }


    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public int getFloor() {
        return floor;
    }
}
