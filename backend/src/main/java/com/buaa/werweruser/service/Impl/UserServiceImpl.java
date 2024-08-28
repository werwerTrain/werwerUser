package com.buaa.werweruser.service.Impl;

import com.buaa.werweruser.entity.User;
import com.buaa.werweruser.mapper.IUserMapper;
import com.buaa.werweruser.service.IUserService;
import com.buaa.werweruser.util.MD5Util;
import com.buaa.werweruser.util.SaltGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMapper userMapper;

    @Override
    public int findUserById(String id) {
        return userMapper.findUserById(id);
    }


    @Override
    public User findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public User registerUser(String id, String name, String password, String email) {
        String salt = SaltGenerator.generateSalt();
        String DBPass = MD5Util.md5(password, salt);
        //String DBPass = password;
        userMapper.registerUser(id, name, DBPass, email, salt);
        return userMapper.findById(id);
    }

    @Override
    public User login(String id, String password) {
        String salt = userMapper.getUserSalt(id);
        String DBPass = MD5Util.md5(password, salt);
        return userMapper.login(id, DBPass);
    }

    @Override
    public Integer updatePassword(String userId, String newpassword) {
        String salt = userMapper.getUserSalt(userId);
        String DBPass = MD5Util.md5(newpassword, salt);
        return userMapper.updatePassword(userId, DBPass);
    }

    @Override
    public String getEmail(String userId) {
        return userMapper.getEmail(userId);
    }

    @Override
    public String getUserSalt(String userId) {
        return userMapper.getUserSalt(userId);
    }
}



