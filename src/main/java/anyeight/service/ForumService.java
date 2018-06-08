package anyeight.service;

import anyeight.model.AnswerPost;
import anyeight.model.News;
import anyeight.model.Post;

import java.util.Date;
import java.util.List;

/**
 * Created by 啊 on 2017/6/3.
 */
public interface ForumService {
    /*添加一个帖子*/
    public String addPost(String user_id, Date date, String text, String title);
    /*得到帖子列表（已按热度排序）*/
    public List<Post> getPostList();
    /*获得一个帖子*/
    public Post getOnePost(String post_id);
    /*获得一个帖子的全部回复*/
    public List<AnswerPost> getAnswerPost(String post_id);

    /*添加一个帖子的回复*/
    public void addAnswer(String origin_id,String user_id, Date date,String text);
    /*删除一个帖子,若不成功说明这个用户没有权限删除*/
    public Boolean delPost(String user_id,String post_id);
    /*删除一个帖子的回复*/
    public Boolean delAnswer(String user_id,String postAnswer_id);
    /**
     * 获得获得二十篇热门文章
     */
    public List<News> getNews(String idName);


}
