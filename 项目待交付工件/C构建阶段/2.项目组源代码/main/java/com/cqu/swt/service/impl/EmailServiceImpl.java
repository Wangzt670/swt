package com.cqu.swt.service.impl;

import com.cqu.swt.common.MailUtils;
import com.cqu.swt.entity.Email;
import com.cqu.swt.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Slf4j
@Service
public class EmailServiceImpl implements EmailService {


    @Async("taskExecutor")
    @Override
    public void senderEmail(Email email) {
            MailUtils.sendMail(email.getUsername(),email.getCode());
    }
}
