package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.entity.OrderDetail;
import com.cqu.swt.mapper.OrderDetailMapper;
import com.cqu.swt.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}