package com.netcracker.edu.dao;

import java.util.List;
import com.netcracker.edu.model.UserInfo;

public interface UserDao {
//    public void add(UserInfo user);
    public UserInfo getUserByUsername(String username);
    public List<UserInfo> getAll();
}
