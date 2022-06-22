package com.cqu.swt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.Category;
import com.cqu.swt.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类（返回json类型数据）
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

//    /**
//     * 分页查询
//     * @param category
//     * @return
//     */
//    @GetMapping("/page")
//    public R<Page> page(Category category) {
//        //分页构造器
//        Page<Category> pageInfo = new Page<>();
//        //条件构造器
//        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
//        //添加排序条件，根据sort进行排序
//        queryWrapper.orderByAsc(Category::getSort);
//        //进行分页查询
//        categoryService.page(pageInfo,queryWrapper);
//        return R.success(pageInfo) ;
//    }
    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }


}
