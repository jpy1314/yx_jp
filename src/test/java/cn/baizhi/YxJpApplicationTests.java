package cn.baizhi;

import cn.baizhi.controller.AdminController;
import cn.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class YxJpApplicationTests {
    @Autowired
//     private AdminDao adminDao;
    private AdminController ac;


    @Test
    void contextLoads() {
//        Admin admin = adminDao.queryByUserName("2021");
//        System.out.println(admin);
        Admin jpy1 = new Admin("9", "jpy", "123456", 1);
        Map<String, Object> login = ac.login(jpy1);
        System.out.println(login);
    }

}
