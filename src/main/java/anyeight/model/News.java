package anyeight.model;

import java.util.Date;

/**
 * Created by T5-SK on 2017/6/9.
 */
public class News {
    /**
     * 新闻标题
     */
    private String title ;
    /**
     * 新闻事件
     */
    private Date time;
    /**
     * 新闻链接
     */
    private String url;
    /**
     * 新闻类型
     */
    private String type;
    /**
     * 新闻相关股票ID
     */
    private String code;

    public String getTitle() {
        return title;
    }

    public Date getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
