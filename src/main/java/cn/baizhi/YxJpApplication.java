package cn.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("cn.baizhi.dao")
@SpringBootApplication
public class YxJpApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxJpApplication.class, args);
    }

}
