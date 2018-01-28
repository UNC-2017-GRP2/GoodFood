package com.netcracker.service.impl;

import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User getByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    @Override
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        userRepository.updateUser(oldUser, newUser);
    }

    @Override
    public boolean isLoginExist(String login) {
        return userRepository.isLoginExist(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
    @Override
    public boolean isEmailExist(String email) {
        return userRepository.isEmailExist(email);
    }

    @Override
    public boolean isYourLoginForUpdateUser(String login, String password) {
        return userRepository.isYourLoginForUpdateUser(login, password);
    }

    @Override
    public boolean isYourEmailForUpdateUser(String email, String password) {
        return userRepository.isYourEmailForUpdateUser(email,password);
    }

    @Override
    public boolean isEqualsPassword(String password, BigInteger userId) {
        return userRepository.isEqualsPassword(password,userId);
    }

    @Override
    public void updatePassword(BigInteger userId, String password) {
        userRepository.updatePassword(userId,password);
    }

    @Override
    public void updateAddresses(BigInteger userId, List<String> addresses) {
        userRepository.updateAddresses(userId,addresses);
    }
}
