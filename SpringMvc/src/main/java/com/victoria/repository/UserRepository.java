package com.victoria.repository;

import com.victoria.model.User;
import java.util.List;

public interface UserRepository {
    User getUserByUsername(String username);
    List<User> getAll();
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
}
