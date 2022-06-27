package com.cqu.swt.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqu.swt.common.R;
import com.cqu.swt.common.RedisUtils;
import com.cqu.swt.entity.User;
import com.cqu.swt.mapper.UserMapper;
import com.cqu.swt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author wkf
 * @version 1.0
 * @date 2022/6/24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Resource
    private RedisUtils redisUtils;

    @Autowired
    private HttpServletRequest request;
    @Override
    public R<String> logout() {

        Long userId = (Long)request.getSession().getAttribute("user");
        redisUtils.deleteUserFromRedis(userId.toString());
        request.getSession().removeAttribute("user");
        return R.success("退出成功！");
    }
}
