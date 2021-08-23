package cn.baizhi.Aliyun;

import cn.baizhi.util.AliyunOss;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.UUID;

public class AliyunUpload {
    //上传字节数组的上传
    public  static  void upload(MultipartFile photo,String FileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOss.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOss.ACCESSKEYID;
        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 填写字符串。
        byte[] content=null;
        try {
            content = photo.getBytes();
        } catch (Exception e){
            e.printStackTrace();
        }
        // 创建PutObjectRequest对象。
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        String s = UUID.randomUUID().toString();
        PutObjectRequest putObjectRequest = new PutObjectRequest("2021class", FileName, new ByteArrayInputStream(content));
        // 上传字符串。
        ossClient.putObject(putObjectRequest);
//QQ图片20190805184846.jpg
// 关闭OSSClient。
        ossClient.shutdown();
    }
    public  static  void  delete(String fileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliyunOss.ENDPOINT;
// 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliyunOss.ACCESSKEYID;

        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;

        String bucketName = AliyunOss.BUCKETNAME;  //存储空间名
        String objectName = "video/"+fileName;  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();

    }
    //下载
    public static void DownLoad( String headimg){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyunOss.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId =AliyunOss.ACCESSKEYID;
        String accessKeySecret = AliyunOss.ACCESSKEYSECETt;
        String bucketName = "2021class";  //存储空间名
        String objectName = headimg;  //文件名
        String localFile="F:\\headimg\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
