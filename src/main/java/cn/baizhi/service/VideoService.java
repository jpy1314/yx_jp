package cn.baizhi.service;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface VideoService {
    //分页查
    Map<String,Object> queryByPage(@Param("start") int start, @Param("end") int end);
    //删除
    void delete(String id);
    //添加
    void insert(Video video);
    //查一个
    Video queryOne(String id);

}
