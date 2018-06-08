package anyeight.serviceImpl;

import anyeight.model.Post;
import anyeight.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by T5-SK on 2017/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class) //spring的单元测试
@ContextConfiguration({"classpath:spring/spring-*.xml"})    //上下文配置
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    public void getUserPost() throws Exception {
        List<Post> list = userService.getUserPost("123");
        System.out.println();
    }

    @Test
    public void getUserAnswerPost() throws Exception {

    }

}