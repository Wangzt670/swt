package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.entity.User;
import com.cqu.swt.mapper.UserMapper;
import com.cqu.swt.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @author wkf
 * @version 1.0
 * @date 2022/6/24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

}
