package com.netcracker.service.impl;

import com.netcracker.model.Address;
import com.netcracker.model.Order;
import com.netcracker.model.User;
import com.netcracker.repository.OrderRepository;
import com.netcracker.repository.UserRepository;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public BigInteger getObjectId() {
        return userRepository.getObjectId();
    }

    @Override
    public User getByUsername(String username){
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getUserById(BigInteger userId) {
        return userRepository.getUserById(userId);
    }

    @Override
    public void saveUser(User user){
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
    public void updateAddresses(BigInteger userId, List<Address> addresses) {
        userRepository.updateAddresses(userId,addresses);
    }

    @Override
    public void removeUserById(BigInteger userId) throws SQLException {
        for (Order order: orderService.getAllOrders(new Locale("en"))){
            if (order.getUserId().equals(userId))
            orderService.removeOrderById(order.getOrderId());
        }
        userRepository.removeUserById(userId);
    }

    @Override
    public void changeRole(BigInteger userId, String role) {
        userRepository.changeRole(userId, role);
    }

    @Override
    public void saveUserImage(BigInteger userId, String imageName) throws SQLException {
        userRepository.saveUserImage(userId, imageName);
    }
}
