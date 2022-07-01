package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.common.CustomException;
import com.cqu.swt.dto.SetmealDto;
import com.cqu.swt.entity.Setmeal;
import com.cqu.swt.entity.SetmealDish;
import com.cqu.swt.mapper.SetmealMapper;
import com.cqu.swt.service.CategoryService;
import com.cqu.swt.service.SetmealDishService;
import com.cqu.swt.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService service;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加套餐信息
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }


    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> getPage(Integer page, Integer pageSize, String name) {
        // 分页查询
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);

        Page<SetmealDto> setmealDtoPage = new Page<>();
        // 添加条件查询
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        if (name != null){
            wrapper.like(Setmeal::getName, name);
        }
        page(setmealPage,wrapper);
        // 封装dto
        BeanUtils.copyProperties(setmealPage, setmealDtoPage);
        // 对分类名称和套餐菜单封装
        List<SetmealDto> records = setmealPage.getRecords().stream().map(item->{
            SetmealDto setmealDto = new SetmealDto();
            // 设置分类
            String categroyName = categoryService.getById(item.getCategoryId()).getName();
            setmealDto.setCategoryName(categroyName);
            // 查询出所有的dish
            LambdaQueryWrapper<SetmealDish> wr = new LambdaQueryWrapper<>();
            wr.eq(SetmealDish::getSetmealId, item.getId());
            List<SetmealDish> list = service.list(wr);
            setmealDto.setSetmealDishes(list);
            BeanUtils.copyProperties(item, setmealDto);
            return setmealDto;
        }).collect(Collectors.toList());
        // 放到dto分页数据中
        setmealDtoPage.setRecords(records);
        return setmealDtoPage;
    }

    /**
     * 根据id查询套餐信息
     * @param ids
     * @return
     */
    @Override
    public SetmealDto getSetmeal(Long ids) {
        // 根据id查询出套餐信息
        Setmeal setmeal = getById(ids);
        // 查询出套餐菜品信息
        List<SetmealDish> list = service.list(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId, ids));
        // 封装成dto返回
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        String categoryName = categoryService.getById(setmeal.getCategoryId()).getName();
        setmealDto.setSetmealDishes(list);
        setmealDto.setCategoryName(categoryName);
        return setmealDto;
    }

    /**
     * 更新套餐
     * @param setmealDto
     */
    @Override
    @Transactional
    public void updateSetmeal(SetmealDto setmealDto) {
        // 将套餐信息保存
        saveOrUpdate(setmealDto);

        //先把旧表中的关系删除
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,setmealDto.getId());
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);

        // 将套餐含有菜品信息保存
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Map<Long,Integer> map=new HashMap<>();
        for (int i = 0; i < setmealDishes.size(); i++) {
            Long dishId = setmealDishes.get(i).getDishId();
            map.put(dishId,map.getOrDefault(dishId,0)+setmealDishes.get(i).getCopies());
        }

        Set<Long> set=new HashSet<>();
        //去除重复数
        List<SetmealDish> toSaveSetmealDishes=new ArrayList<>();
        for (int i = 0; i < setmealDishes.size(); i++) {
            Long id = setmealDishes.get(i).getDishId();
            if(set.contains(id))continue;
            else{
                setmealDishes.get(i).setCopies(map.getOrDefault(setmealDishes.get(i).getDishId(),1));
                toSaveSetmealDishes.add(setmealDishes.get(i));
                set.add(id);
            }
        }
        toSaveSetmealDishes = toSaveSetmealDishes.stream().map(item->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        service.saveOrUpdateBatch(toSaveSetmealDishes);

    }

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
