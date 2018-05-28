package com.netcracker.signup;

import com.netcracker.model.User;
import com.netcracker.service.UserService;
import com.netcracker.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class MyConnectionSignUp implements ConnectionSignUp {
    /*
    @Autowired
    private UserRepository userRepository;

    public MyConnectionSignUp(UserRepository myUserAccountDAO) {
        this.userRepository = myUserAccountDAO;
    }

    // After login Social.
    // This method is called to create a USER_ACCOUNTS record
    // if it does not exists.
    @Override
    public String execute(Connection<?> connection) {

        User account=    userRepository.createUserAccount(connection);
        return account.getUserId().toString();
    }
    */
    private UserService userService;
    public MyConnectionSignUp(UserService myUserAccountDAO) {
        this.userService = myUserAccountDAO;
    }


    // After login Social.
    // This method is called to create a USER_ACCOUNTS record
    // if it does not exists.
    @Override
    public String execute(Connection<?> connection) {

        User account=   userService.createUserAccount(connection);
        return account.getUserId().toString();
    }
}
