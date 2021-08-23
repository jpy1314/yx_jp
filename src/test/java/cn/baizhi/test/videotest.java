package cn.baizhi.test;

import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class videotest {
    @Autowired
    private VideoDao vd;
    @Test
    public void context(){
        List<Video> videos = vd.queryByPage(0, 2);
        for (Video video : videos) {
            System.out.println(video);
        }
    }
}
