package com.buaa.werweruser.service.Impl;

import com.buaa.werweruser.entity.VerificationCode;
import com.buaa.werweruser.mapper.IVerificationCodeMapper;
import com.buaa.werweruser.service.IVerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("verificationCodeService")
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    @Autowired
    private IVerificationCodeMapper verificationCodeMapper;

    @Override
    public VerificationCode getVerificationCode(String email) {
        return verificationCodeMapper.getVerificationCode(email);
    }

    @Override
    public void addVerificationCode(VerificationCode code) {
        verificationCodeMapper.addVerificationCode(code);
    }
}
