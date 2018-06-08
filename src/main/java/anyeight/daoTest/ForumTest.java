package anyeight.daoTest;

import anyeight.dao.ForumMapper;
import anyeight.model.AnswerPost;
import anyeight.model.News;
import anyeight.model.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.List;

/**
 * Created by T5-SK on 2017/6/4.
 */

@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class ForumTest {

    @Autowired
    ForumMapper forumMapper;

    @Test
    public void testAddPost(){
        Date date = Date.valueOf("2017-05-01");
        System.out.println(forumMapper.addPost("123",date,"lalala","hahaha"));
    }

    @Test
    public void testGetPostList(){
        List<Post> list = forumMapper.getPostList();
        System.out.println(list.get(0).getUser_id());
        System.out.println(list.get(1).getUser_id());
    }

    @Test
    public void testGetNews(){
        List<News> list = forumMapper.getNewsByID("lal");
        System.out.println("lal");
    }

    @Test
    public void testGetInstantNews(){
        List<News> list = forumMapper.getNewsInstantNews();
        System.out.println("lal");
    }

    @Test
    public void testGetUserPost(){
        List<Post> list = forumMapper.getUserPost("100008");
        System.out.println();
    }

    @Test
    public void testGetUserAnswerPost(){
        List<AnswerPost> list = forumMapper.getUserAnswerPost("100008");
        System.out.println();
    }

}
