package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.common.BaseContext;
import com.cqu.swt.common.CustomException;
import com.cqu.swt.common.R;
import com.cqu.swt.dto.OrdersDto;
import com.cqu.swt.entity.*;
import com.cqu.swt.mapper.OrderDetailMapper;
import com.cqu.swt.mapper.OrderMapper;
import com.cqu.swt.mapper.UserMapper;
import com.cqu.swt.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(wrapper);
    }
    @Override
    public R<Page> pageQuery(Page pageInfo, String number, String beginTime, String endTime) {
        Page<OrdersDto> ordersDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber,number);
        queryWrapper.between(StringUtils.isNotEmpty(beginTime) || StringUtils.isNotEmpty(endTime),Orders::getOrderTime,beginTime,endTime);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        orderMapper.selectPage(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");
        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> ordersDtoList = new ArrayList<>();
        for (Orders record : records) {
            OrdersDto ordersDto = new OrdersDto();
            BeanUtils.copyProperties(record,ordersDto);
            Long userId = record.getUserId();

            User user = userMapper.selectById(userId);

            if (user != null){
                String name = user.getName();
                ordersDto.setUserName(name);
            }
            ordersDtoList.add(ordersDto);
        }
        ordersDtoPage.setRecords(ordersDtoList);
        return R.success(ordersDtoPage);
    }

    @Override
    public void updateStatusById(OrdersDto ordersDto) {
        Long id = ordersDto.getId();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Orders::getId,id);
        Orders orders = orderMapper.selectOne(queryWrapper);
        orders.setStatus(ordersDto.getStatus());
        orderMapper.updateById(orders);
    }

    @Override
    public R<Page> userPage(Page pageInfo) {

        try {
            Page<OrdersDto> dtoPage = new Page<>();
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(Orders::getOrderTime);
            queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
            orderMapper.selectPage(pageInfo,queryWrapper);
            BeanUtils.copyProperties(pageInfo,dtoPage,"records");
            List<Orders> records = pageInfo.getRecords();
            List<OrdersDto> ordersDtoList = new ArrayList<>();
            for (Orders record : records) {
                OrdersDto ordersDto = new OrdersDto();
                BeanUtils.copyProperties(record,ordersDto);
                Long userId = record.getUserId();
                LambdaQueryWrapper<OrderDetail> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(OrderDetail::getOrderId,record.getId());
                List<OrderDetail> orderDetails = orderDetailMapper.selectList(queryWrapper1);
                ordersDto.setOrderDetails(orderDetails);

                User user = userMapper.selectById(userId);

                if (user != null){
                    String name = user.getName();
                    ordersDto.setUserName(name);
                }
                ordersDtoList.add(ordersDto);
            }
            dtoPage.setRecords(ordersDtoList);
            return R.success(dtoPage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error("订单数据加载失败");
        }

    }


}