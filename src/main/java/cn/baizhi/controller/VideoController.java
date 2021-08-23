package cn.baizhi.controller;

import cn.baizhi.Aliyun.AliyunUpload;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import cn.baizhi.util.AliyunOss;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController  {
    @Autowired
    private VideoService vs;
    @RequestMapping("/findAll")
    public Map<String,Object> findAll(int page){
        int size = 3;
        return  vs.queryByPage(page, size);
    }
    @RequestMapping("/add")
    public void  add(MultipartFile video,String title,String brief ,String id){
        System.out.println(video.getOriginalFilename());

//        System.out.println(file);
        System.out.println(title);
        System.out.println(brief);
        System.out.println(id);
      String fileName=new Date().getTime()+video.getOriginalFilename();
        AliyunUpload.upload(video, "video/"+fileName);

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyunOss.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOss.ACCESSKEYID;
        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;
// 填写视频文件所在的Bucket名称。
        String bucketName ="2021class" ;
// 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        String objectName ="video/"+fileName;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_2000,f_jpg,w_800,h_600";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);//String 路径

// 填写网络流地址。
        InputStream inputStream=null;

        try {
            inputStream = new URL(signedUrl.toString()).openStream();

        }catch (Exception e){
            e.printStackTrace();
        }
        String[] split = fileName.split("\\.");
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        ossClient.putObject("2021class", "video/"+split[0]+".jpg", inputStream);
// 关闭OSSClient。
        ossClient.shutdown();
        Video video1 = new Video();
        video1.setId(UUID.randomUUID().toString());
        video1.setTitle(title);
        video1.setBrief(brief);
        video1.setCoverPath("http://2021class.oss-cn-beijing.aliyuncs.com/"+"video/"+split[0]+".jpg");
        video1.setCreateDate(new Date());
        video1.setVideoPath("http://2021class.oss-cn-beijing.aliyuncs.com/video/"+fileName);
        Category category = new Category();
        category.setId(id);
        video1.setCategory(category);
        vs.insert(video1);
    }
    @RequestMapping("/delete")
    public  void  delete(String id){
        Video video = vs.queryOne(id);
        String coverPath = video.getCoverPath();//删除封面
//        String[] split = coverPath.split("com/");


        String videoPath = video.getVideoPath();//删除视频
//        String i = "http://2021class.oss-cn-beijing.aliyuncs.com/video/";
        String i =" http://2021class.oss-cn-beijing.aliyuncs.com/video/";
        String substring1 = coverPath.substring(51);
        System.out.println(i.length());
        System.out.println(videoPath);
//        String substring1 = coverPath.substring(51);

        String substring = videoPath.substring(51);
        System.out.println(substring);
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOss.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOss.ACCESSKEYID;

        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;

        String bucketName = "2021class";  //存储空间名
        String objectName ="video/"+ substring;  //文件名
//        http://2021class.oss-cn-beijing.aliyuncs.com/video/16291272681932.mp4
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
//        System.out.println(split[0]);
//        System.out.println(split[1]);

        AliyunUpload.delete(substring1);


        vs.delete(id);

    }
}
