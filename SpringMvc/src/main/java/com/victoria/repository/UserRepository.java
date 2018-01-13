package com.victoria.repository;

import com.victoria.model.User;
import java.util.List;

public interface UserRepository {
    User getUserByUsername(String username);
    List<User> getAll();
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
}
