package com.victoria.service.impl;

import com.victoria.model.User;
import com.victoria.repository.UserRepository;
import com.victoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
