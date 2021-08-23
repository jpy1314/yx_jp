package cn.baizhi.test;
  
import cn.baizhi.entity.Admin;
import lombok.extern.java.Log;
  
/** 
 * Created by zhangzh on 2017/2/8. 
 */  
@Log  
public class LombokTest {  
  
    public static void main(String[] args) {

        Admin admin = new Admin();
        System.out.println(admin);
        admin.setId("1");
        admin.setPassword("菜团子");
        System.out.println(admin.toString());
        System.out.println(admin.getId());
        System.out.println("===================");
        Admin admin1 = new Admin("1", "2", "3", 4);
        System.out.println(admin1);
    }  
}  