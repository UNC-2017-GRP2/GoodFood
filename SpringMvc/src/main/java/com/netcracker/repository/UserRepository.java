package com.netcracker.repository;

import com.netcracker.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserRepository {
    User getUserByUsername(String username);
    List<User> getAllUsers();
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
