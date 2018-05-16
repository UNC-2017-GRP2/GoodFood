package com.netcracker.signup;

import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

public class MyConnectionSignUp implements ConnectionSignUp {
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

}
