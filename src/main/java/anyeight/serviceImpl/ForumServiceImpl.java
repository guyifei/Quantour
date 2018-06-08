package anyeight.serviceImpl;

import anyeight.dao.ForumMapper;
import anyeight.model.AnswerPost;
import anyeight.model.News;
import anyeight.model.Post;
import anyeight.service.ForumService;
import anyeight.util.MathHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by 啊 on 2017/6/3.
 */
@Service
public class ForumServiceImpl implements ForumService{
    @Autowired
    ForumMapper forumMapper;
    @Override
    public String addPost(String user_id, Date date, String text, String title) {
        String str=forumMapper.addPost(user_id, MathHelper.utilToSql(date),text,title);
        if(str!=null)
            return str;
        else
            return "";
    }

    @Override
    public List<Post> getPostList() {
        List list=forumMapper.getPostList();
        if(list!=null)
            return list;
        else{
            List list1=new ArrayList();
            return list1;
        }
    }

    @Override
    public Post getOnePost(String post_id) {
        Post post= forumMapper.getPost(post_id);
        if(post!=null)
            return post;
        else{
            Post post1=new Post("","",null,"","",-1,-1);
            return post1;
        }
    }

    @Override
    public List<AnswerPost> getAnswerPost(String post_id) {
        List list=forumMapper.getAnswerOfPost(post_id);
        if(list!=null)
            return list;
        else{
            List list1=new ArrayList();
            return list1;
        }
    }


    @Override
    public void addAnswer(String origin_id,String user_id, Date date,String text) {
        Post post=forumMapper.getPost(origin_id);
        if (post != null) {
            post.setDate(MathHelper.utilToSql(date));
            post.setHotPoint(post.getHotPoint() + 1);
            post.setCurrent_size(post.getCurrent_size() + 1);
            forumMapper.addAnswer(origin_id, user_id, MathHelper.utilToSql(date), text, post.getHotPoint());
            forumMapper.updatePost(post);
        }
    }

    @Override
    public Boolean delPost(String user_id,String post_id) {
        Post post=forumMapper.getPost(post_id);
        if(post!=null) {
            if (!user_id.equals(post.getUser_id()))
                return false;
            forumMapper.delPost(post_id);
            forumMapper.delAllAnswer(post_id);
            return true;
        }
        else
            return false;
    }

    @Override
    public Boolean delAnswer(String user_id,String postAnswer_id) {
        AnswerPost answerPost=forumMapper.getAnswer(postAnswer_id);
        if(answerPost==null) {
            if (!user_id.equals(answerPost.getUser_id()))
                return false;
            forumMapper.delAnswer(postAnswer_id);
            Post post = forumMapper.getPost(answerPost.getOrigin_id());
            post.setHotPoint(post.getHotPoint() - 1);
            post.setCurrent_size(post.getCurrent_size() - 1);
            forumMapper.updatePost(post);
            return true;
        }
        else
            return false;
    }

    @Override
    public List<News> getNews(String idName) {
        List<News> list = new ArrayList<>();
        List list1=forumMapper.getNewsByID(idName);
        List list2=forumMapper.getNewsByName(idName);
        if(list1!=null)
            list.addAll(list1);
        if(list2!=null)
            list.addAll(list2);
        return list;
    }


}
