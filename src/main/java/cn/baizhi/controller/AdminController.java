package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping("login")
    public Map<String, Object> login(@RequestBody Admin admin){
        Map<String, Object> map = new HashMap<>();
        Admin admin1 = adminService.findOne(admin.getUsername());
//        logger.info(admin1.toString());
        logger.info(admin.getUsername());
        logger.info(admin.getPassword());

        map.put("flag",false );//登录失败
     if (admin1!=null){
         if (admin1.getPassword().equals(admin.getPassword())){
             //用户名密码都相同
             map.put("flag",true );//登录成功
             map.put("admin", admin1);
         }else {
             //密码错误
             map.put("msg","密码错误" );
         }
     }else {
         map.put("msg","用户不存在" );
     }
     return map;
    }

}
