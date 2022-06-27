package com.cqu.swt.service;

import com.cqu.swt.entity.Email;

public interface EmailService {
    /**用于注册成功后发送邮件 @param Email 验证码*/
    void senderEmail(Email email);
}
