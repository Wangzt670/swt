package com.cqu.swt.dto;

import com.cqu.swt.entity.Dish;
import com.cqu.swt.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装页面传输的 数据
 */
@Data
@ApiModel("菜品Dto")
public class DishDto extends Dish implements Serializable {

    @ApiModelProperty("菜品口味")
    private List<DishFlavor> flavors = new ArrayList<>();

    @ApiModelProperty("分类名")
    private String categoryName;

    @ApiModelProperty("数据")
    private Integer copies;
}
