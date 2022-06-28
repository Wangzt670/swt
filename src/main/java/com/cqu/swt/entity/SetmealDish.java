package com.cqu.swt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//菜品关系实体映射
@Data
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键id")

    private Long id;

    @ApiModelProperty("套餐id")

    //套餐id
    private Long setmealId;

    @ApiModelProperty("菜品id")

    //菜品id
    private Long dishId;

    @ApiModelProperty("菜品名称")

    //菜品名称 （冗余字段）
    private String name;
    @ApiModelProperty("菜品原价")

    //菜品原价
    private BigDecimal price;
    @ApiModelProperty("份数")

    //份数
    private Integer copies;

    @ApiModelProperty("排序")

    //排序
    private Integer sort;

    @ApiModelProperty("创建时间")

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人")

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @ApiModelProperty("更新人")

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @ApiModelProperty("是否删除")

    //是否删除
    private Integer isDeleted;
}
