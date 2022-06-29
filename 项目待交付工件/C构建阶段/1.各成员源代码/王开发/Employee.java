package com.cqu.swt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("员工")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键 ")
    private Long id;
    @ApiModelProperty("用户姓名 ")
    private String username;
    @ApiModelProperty("昵称")
    private String name;
    @ApiModelProperty("密码 ")
    private String password;
    @ApiModelProperty("手机号 ")
    private String phone;
    @ApiModelProperty("性别 ")
    private String sex;
    @ApiModelProperty("id")
    private String idNumber;//对应数据库中id_number，已开启驼峰映射
    @ApiModelProperty("状态 ")
    private Integer status;

    @TableField(fill = FieldFill.INSERT)  //插入时填充字段
    @ApiModelProperty("创建时间 ")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)  //插入和更新时填充字段
    @ApiModelProperty("更新时间 ")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)  //插入时填充字段
    @ApiModelProperty("创建人 ")
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)  //插入和更新时填充字段
    @ApiModelProperty("更新人 ")
    private Long updateUser;

}
