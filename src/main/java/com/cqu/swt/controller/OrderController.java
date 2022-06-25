package com.cqu.swt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cqu.swt.common.R;
import com.cqu.swt.dto.OrdersDto;
import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.entity.Orders;
import com.cqu.swt.service.OrderDetailService;
import com.cqu.swt.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }


    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String number, String beginTime, String endTime){

        Page pageInfo = new Page<>(page,pageSize);
        return orderService.pageQuery(pageInfo,number,beginTime,endTime);
    }
    @PutMapping
    public R<String> status(@RequestBody OrdersDto ordersDto){
        orderService.updateStatusById(ordersDto);
        return R.success("修改订单状态成功！");
    }

    @GetMapping("/userPage")
    public R<Page> userPage(int page,int pageSize){
        Page pageInfo = new Page<>(page,pageSize);
        return orderService.userPage(pageInfo);
    }


    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public R<String> deleteOrder(Long id){
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        orderDetailService.remove(queryWrapper);
        orderService.removeById(id);
        return R.success("删除成功！");
    }


    @PostMapping("/again")
    public R<String> addOrderAgain(@RequestBody Orders orders){
        if (orders.getId() != null){
            return R.success("成功！");
        }
        return R.error("失败!");
    }

}