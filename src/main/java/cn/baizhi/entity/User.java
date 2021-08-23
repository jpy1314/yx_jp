package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User implements Serializable {
    @Excel(name="id")
    private  String id;
    @Excel(name="姓名")
    private  String username;
    @Excel(name="手机号")
    private  String phone;
    @Excel(name="头像" ,type = 2)
    private  String headimg;
    @Excel(name="描述")
    private  String brief;
    @Excel(name="微信")
    private String wechat;
    @JsonFormat(pattern = "yyyy-dd-MM " )
    @Excel(name="时间",format = "yyyy-MM-dd")
    private Date createdate;
    @Excel(name="状态")
    private  Integer status;
    @Excel(name="性别")
    private String sex;
}
