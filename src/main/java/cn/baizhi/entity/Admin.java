package cn.baizhi.entity;

import lombok.*;

import java.io.Serializable;

//管理员实体类
@Setter   //生成set方法
@Getter   //生成get方法
//@ToString //toString 方法
@AllArgsConstructor //生成全参构造方法
@NoArgsConstructor //无参构造
@Data //@setter + @getter + @ToString


public class Admin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;


}
