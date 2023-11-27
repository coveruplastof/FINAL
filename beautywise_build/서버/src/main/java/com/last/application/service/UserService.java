package com.last.application.service;

import com.last.application.dao.UserMapper;
import com.last.domain.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;  // 추가
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void saveUser(User user) {
        userMapper.insertUser(user);
    }
    public void deleteUserByToken(String accessToken) {
        userMapper.deleteUserByToken(accessToken);
    }

}