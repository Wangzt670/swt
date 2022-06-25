package com.cqu.swt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cqu.swt.common.R;
import com.cqu.swt.dto.OrdersDto;
import com.cqu.swt.entity.Orders;

import java.util.Map;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    public void submit(Orders orders);


    /**
     * 后台订单明细分页查询
     * @param pageInfo
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    R<Page> pageQuery(Page pageInfo, String number, String beginTime, String endTime);

    /**
     * 通过订单号修改订单状态
     * @param ordersDto
     */
    void updateStatusById(OrdersDto ordersDto);

    /**
     * 用户订单分页查询
     * @param pageInfo
     * @return
     */
    R<Page> userPage(Page pageInfo);


}
