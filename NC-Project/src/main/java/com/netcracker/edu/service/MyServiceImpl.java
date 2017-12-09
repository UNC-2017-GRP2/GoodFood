package com.netcracker.edu.service;

import com.netcracker.edu.dao.UserDao;
import com.netcracker.edu.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyServiceImpl implements MyService{

    @Autowired
    private UserDao userDao;

    public UserInfo getByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    public List<UserInfo> list() {
        return userDao.getAll();
    }
}


