package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
    //分页查
    List<Video> queryByPage(@Param("start") int start,@Param("end") int end);
    //总条数
    Integer content();
    //删除
    void delete(String id);
    //添加
    void insert(Video video);
    //查一个
    Video queryOne(String id);
}
