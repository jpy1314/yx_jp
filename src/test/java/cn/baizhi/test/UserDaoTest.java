package cn.baizhi.test;

import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao ud;
    @Autowired
    private UserService us;
    @Test
    public  void testQueryRange(){
        List<User> list = ud.queryRange(0, 3);
        for (User user : list) {
            System.out.println(user);
        }
    }
    @Test
    public void testConten(){
        Integer content = ud.content();
        System.out.println(content);
    }
    @Test
    public  void  testupdate(){
        us.update(0, "1");
    }
    @Test
    public  void queryAlltest(){
        List<User> list = us.queryAll();
        for (User user : list) {
            System.out.println(user);
        }
    }
}
