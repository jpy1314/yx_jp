package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl  implements  VideoService{
    @Autowired
    private VideoDao vd;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        //总条数
        Integer content = vd.content();
        System.out.println(content);
        map.put("conten",content );
        //当前第几页
        if (content%size==0){
            map.put("num",content/size );
        }else {
            map.put("num", content/size+1);
        }
        //
        List<Video> videos = vd.queryByPage((page - 1) * size, size);
        map.put("data",videos );
        return map;
    }
    @DeleteCache
    @Override
    public void delete(String id) {
        vd.delete(id);
    }
    @DeleteCache
    @Override
    public void insert(Video video) {
vd.insert(video);
    }

    @Override
    public Video queryOne(String id) {
        return  vd.queryOne(id);

    }
}
