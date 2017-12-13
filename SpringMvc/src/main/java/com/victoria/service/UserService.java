package com.victoria.service;

import com.victoria.model.User;

public interface UserService {
    User getByUsername(String username);
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
}
