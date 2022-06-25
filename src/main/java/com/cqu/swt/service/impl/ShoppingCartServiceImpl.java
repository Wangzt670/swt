package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.entity.ShoppingCart;
import com.cqu.swt.mapper.ShoppingCartMapper;
import com.cqu.swt.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
