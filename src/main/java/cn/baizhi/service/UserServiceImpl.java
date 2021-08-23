package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.Aliyun.AliyunUpload;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.AliyunOss;
import cn.baizhi.vo.MonthAndCount;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao ud;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        //总条数
        Integer content = ud.content();
        System.out.println(content);
//        map.put("conten",content );
        //当前第几页
        if (content%size==0){
            map.put("num",content/size );
        }else {
            map.put("num", content/size+1);
        }
        //
        List<User> list = ud.queryRange((page - 1) * size, size);
        map.put("data",list );
        return map;
    }
    @DeleteCache
    @Override
    public void update(int status, String id) {
     ud.update(status, id);
    }
    @DeleteCache
    @Override
    public void sava(MultipartFile photo,User user) {
//        user.setId(UUID.randomUUID().toString());
//        user.setStatus(0);
//        user.setCreatedate(new Date());
//        ud.sava(user);
        //头像上传
        String fileName = photo.getOriginalFilename();
        Date date = new Date();
        long time = date.getTime();
        String finalName = time+fileName;
        AliyunUpload.upload(photo, finalName);
        user.setId(UUID.randomUUID().toString());
        user.setCreatedate(new Date());
        //
        user.setHeadimg("http://2021class.oss-cn-beijing.aliyuncs.com/"+finalName);
        user.setStatus(0);
        user.setSex("女");
        ud.sava(user);
    }
    @Override
    public User queryOne(String id) {
       return  ud.queryOne(id);
    }
    @DeleteCache
    @Override
    public void delete(String id) {
         ud.delete(id);
    }
    @Override
    public List<User> queryAll()  {

        List<User> list = ud.queryAll();
        for (int i=0;i<list.size();i++){
            User user = list.get(i);
            String headimg = user.getHeadimg();
            String[] split = headimg.split("com/");
            AliyunUpload.DownLoad(split[1]);
            user.setHeadimg("F:\\headimg\\"+split[1]);
        }

        return ud.queryAll();
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<MonthAndCount> queryBySex(String sex) {
        return  ud.selectBySex(sex);

    }


}
