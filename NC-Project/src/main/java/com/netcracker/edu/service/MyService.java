package com.netcracker.edu.service;

import com.netcracker.edu.model.UserInfo;

import java.util.List;

public interface MyService {
    public UserInfo getByUsername(String username);
    public List<UserInfo> list();
}
