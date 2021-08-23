package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //范围查询
    List<User> queryRange(@Param("start") int start,@Param("end") int end);
    //查询总页数
    Integer content();
    //修改状态
    void update(@Param("status") int status,@Param("id")  String id);
    //添加用户
    void sava(User user);
    //查一个
    User queryOne(String id);
    //删除一个
    void delete(String id);
    //查所有
    List<User> queryAll();
    //根据性别查人数
    List<MonthAndCount> selectBySex(String sex);
}
