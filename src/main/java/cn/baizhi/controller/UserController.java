package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import cn.baizhi.util.AliyunOss;
import cn.baizhi.util.EasyPoi;
import cn.baizhi.vo.MonthAndCount;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService us;
    @RequestMapping("/queryByPage")
    public Map<String,Object> queryByPage(int page){
     int size = 3;
      return   us.queryByPage(page, size);
    }
    @RequestMapping("/update")
    public  void  update(int status ,String id){
        us.update(status, id);
    }


    @RequestMapping("/add")
    public  void add(MultipartFile photo ,String username,String phone ,String brief) throws IOException {
        System.out.println(photo.getOriginalFilename());
        User user = new User(null, username, phone, null, brief, null, null, null,null);
        us.sava(photo,user);
    }

    @RequestMapping("/delete")
    public void deleteBucket(String id){
        User user = us.queryOne(id);
        String s = user.getHeadimg();
        System.out.println(user);
        String i = "http://2021class.oss-cn-beijing.aliyuncs.com/";

        String substring = s.substring(45);
        System.out.println(substring);


// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOss.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOss.ACCESSKEYID;

        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;

        String bucketName = AliyunOss.BUCKETNAME;  //存储空间名
        String objectName = substring;  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();


        us.delete(id);
    }
    @RequestMapping("/queryAll1")
    public List<User> queryAll1() {
        List<User> list = us.queryAll();
        System.out.println(list);
        EasyPoi.EasyPoi(list);
        return list;
    }
    @RequestMapping("/registCount")
    public Map<String, Object> queryCount(){
//        System.out.println("zhixingle ");
//        List<String> data = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月");
//        List<Integer> manCount = Arrays.asList(50, 200, 360, 100, 100, 300);
//        List<Integer> womanCount = Arrays.asList(50, 200, 360, 100, 100, 300);
//        Map<String, Object> map = new HashMap<>();
//        map.put("data", data);
//        map.put("manCount", manCount);
//        map.put("womanCount", womanCount);
//
//     return map;
        Map<String, Object> map = new HashMap<>();
        List<MonthAndCount> man = us.queryBySex("男");
        List<Integer> list = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (MonthAndCount monthAndCount : man) {
            int i1 = Integer.parseInt(monthAndCount.getMonth());
            for (int i=1;i<=12;i++){
                if (i1==i){
                    list.set(i-1, monthAndCount.getSexCount());
                }
            }
        }
        map.put("list", list);
        List<MonthAndCount> woman = us.queryBySex("女");
        List<Integer> list1 = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (MonthAndCount count : woman) {
            int i2 = Integer.parseInt(count.getMonth());
            for (int i=1;i<=12;i++){
                if (i2==i){
                    list1.set(i-1, count.getSexCount());
                }
            }
        }
        map.put("list1", list1);
        List<String> data =new ArrayList<>();
        for (int i=1; i<=12;i++){
            data.add(i+"月");
        }
        map.put("data", data);
        return  map;
    }
}
