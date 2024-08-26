package com.buaa.werweruser.service.Impl;

import com.buaa.werweruser.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service("emailService")
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("werwertrip@163.com");
        mailSender.send(message);
    }
}

