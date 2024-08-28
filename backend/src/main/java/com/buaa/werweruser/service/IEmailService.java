package com.buaa.werweruser.service;

import org.springframework.stereotype.Component;

@Component
public interface IEmailService {
    public void sendSimpleMail(String to, String subject, String text);
}
