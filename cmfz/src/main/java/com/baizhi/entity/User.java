package com.baizhi.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class User {
    private int id;
    private String phoneNum;
    private String username;
    private String password;
    private String salt;
    private String dharmalName;
    private String province;
    private String city;
    private String sex;
    private String sign;
    private String headPic;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date date;
}
