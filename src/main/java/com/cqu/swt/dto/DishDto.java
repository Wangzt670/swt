package com.cqu.swt.dto;

import com.cqu.swt.entity.Dish;
import com.cqu.swt.entity.DishFlavor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装页面传输的 数据
 */
@Data
public class DishDto extends Dish implements Serializable {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
