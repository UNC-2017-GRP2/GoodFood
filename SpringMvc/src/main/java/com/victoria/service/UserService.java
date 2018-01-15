package com.victoria.service;

import com.victoria.model.User;

import java.math.BigInteger;

public interface UserService {
    User getByUsername(String username);
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
    boolean isEqualsPassword(String password, BigInteger userId);
    void updatePassword(BigInteger userId, String password);
}
