package com.cqu.swt.controller;

import com.cqu.swt.common.R;
import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.service.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单明细
 */
@Slf4j
@RestController
@RequestMapping("/orderDetail")
@Api(tags = "订单内容相关接口")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;


    @GetMapping("/{id}")
    @ApiOperation(value = "获取主键接口")
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    public R<List<OrderDetail>> getById(@PathVariable Long id){

        return  orderDetailService.getByOrderId(id);

    }

}