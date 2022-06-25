package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.mapper.OrderDetailMapper;
import com.cqu.swt.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Override
    public R<List<OrderDetail>> getByOrderId(Long id) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper);
        AtomicInteger amount = new AtomicInteger(0);
        return R.success(orderDetails);
    }
}