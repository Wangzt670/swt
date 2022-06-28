package com.cqu.swt.dto;


import com.cqu.swt.entity.Setmeal;
import com.cqu.swt.entity.SetmealDish;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("分类Dto")
public class SetmealDto extends Setmeal {

    @ApiModelProperty("套餐菜品")
    private List<SetmealDish> setmealDishes;

    @ApiModelProperty("分类名")
    private String categoryName;
}
