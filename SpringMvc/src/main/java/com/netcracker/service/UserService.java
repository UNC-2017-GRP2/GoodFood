package com.netcracker.service;

import com.netcracker.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getByUsername(String username);
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
    boolean isEqualsPassword(String password, BigInteger userId);
    void updatePassword(BigInteger userId, String password);
    void updateAddresses(BigInteger userId, List<String> addresses);
}
