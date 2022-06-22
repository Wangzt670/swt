package com.cqu.swt.dto;


import com.cqu.swt.entity.Setmeal;
import com.cqu.swt.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
