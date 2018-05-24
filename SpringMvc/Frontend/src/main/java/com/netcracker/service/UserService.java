package com.netcracker.service;

import com.netcracker.form.MyUserAccountForm;
import com.netcracker.model.Address;
import com.netcracker.model.User;

import java.math.BigInteger;
import org.springframework.social.connect.Connection;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getByUsername(String username);
    User findByEmail(String email) throws SQLException;
    User getUserById(BigInteger userId);
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    void changeRole(BigInteger userId, String role);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
    boolean isEqualsPassword(String password, BigInteger userId);
    void updatePassword(BigInteger userId, String password);
    void updateAddresses(BigInteger userId, List<Address> addresses);
    void removeUserById(BigInteger userId);
    public BigInteger getObjectId();
    void saveUserImage(BigInteger userId, String imageName);
    User registerNewUserAccount(MyUserAccountForm userAccountForm);
    User createUserAccount(Connection<?> connection);
}
