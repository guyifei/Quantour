package anyeight.dao;

import anyeight.model.AnswerPost;
import anyeight.model.News;
import anyeight.model.Post;

import java.sql.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/6/3.
 */
public interface ForumMapper {
    /**
     * lalala
     * @param user_id
     * @param date
     * @param text
     * @param title
     * @return
     */
    public String addPost(String user_id, Date date, String text, String title);
    /*得到帖子列表（按热度排序）*/
    public List<Post> getPostList();
    /*添加一个帖子的回复*/
    public void addAnswer(String origin_post,String user_id, Date date, String text,int floor);

    /**
     * 获得一个帖子
     * @param post_id
     * @return
     */
    public Post getPost(String post_id);
    /*更新一个帖子*/
    public void updatePost(Post post);
    /*删除一个帖子*/
    public void delPost(String post_id);
    /*删除一个帖子的所有回帖*/
    public void delAllAnswer(String post_id);
    /*得到一个回复*/
    public AnswerPost getAnswer(String postAnswer_id);
    /*获得一个帖子的所有回复*/
    public List<AnswerPost> getAnswerOfPost(String origin_post);

    /**
     * 删除信息
     * @param postAnswer_id:啦啦啦
     * @return
     */
    public Post delAnswer(String postAnswer_id);
    /*按名字获得股票相关政策*/
    public List<News> getNewsByName(String name);
    /*按ID获得股票相关政策*/
    public List<News> getNewsByID(String id);
    /**
     * 获得最新的二十条即时新闻
     */
    public List<News> getNewsInstantNews();

    /**返回该用户的所有发帖*/
    public List<Post> getUserPost(String user_id);
    /**
     * 返回该用户的所有回帖
     */
    public List<AnswerPost> getUserAnswerPost(String user_id);
}
