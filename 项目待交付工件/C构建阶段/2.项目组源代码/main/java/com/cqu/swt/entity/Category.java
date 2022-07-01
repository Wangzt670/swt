package com.cqu.swt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel("分类")
public class Category {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;


    //类型 1 菜品分类 2 套餐分类
    @ApiModelProperty("分类类型")
    private Integer type;


    //分类名称
    @ApiModelProperty("分类名称")
    private String name;


    //顺序
    @ApiModelProperty("显示顺序")
    private Integer sort;


    //创建时间
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    //更新时间
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    //创建人
    @ApiModelProperty("创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    //修改人
    @ApiModelProperty("修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


//    //是否删除
//    private Integer isDeleted;

}
