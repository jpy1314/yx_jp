package cn.baizhi.service;

import cn.baizhi.entity.Admin;

public interface AdminService {
//    Admin queryByUserName(String username);
    //登录的查一个
    Admin findOne(String username);
}
