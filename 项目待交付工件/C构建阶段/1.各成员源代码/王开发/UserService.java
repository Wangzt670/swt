package com.cqu.swt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cqu.swt.common.R;
import com.cqu.swt.entity.User;
import org.apache.catalina.Service;

/**
 * @author wkf
 * @version 1.0
 * @date 2022/6/24
 */
public interface UserService extends IService<User> {

    /**
     * 用户退出
     * @return
     */
    R<String> logout();
}
