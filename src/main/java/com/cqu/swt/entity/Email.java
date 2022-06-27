package com.cqu.swt.entity;


import lombok.Data;

@Data
public class Email {

    //用户名
    private String username;
    //验证码
    private String code;

}