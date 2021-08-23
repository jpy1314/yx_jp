package cn.baizhi.service;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object>queryByPage(int page ,int size);
    //修改
    void update(int status,String id);
    //添加
    void sava(MultipartFile photo, User user);
    //查一个
    User queryOne(String id);
    //删除
    void delete(String id);
    //查所有
    List<User> queryAll();
    //根据性别查人数
    List<MonthAndCount> queryBySex(String sex);

}
