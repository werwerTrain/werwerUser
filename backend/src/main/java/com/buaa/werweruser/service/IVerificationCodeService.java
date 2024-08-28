package com.buaa.werweruser.service;

import com.buaa.werweruser.entity.VerificationCode;
import org.springframework.stereotype.Component;

@Component
public interface IVerificationCodeService {
    public VerificationCode getVerificationCode(String email);

    public void addVerificationCode(VerificationCode code);
}
