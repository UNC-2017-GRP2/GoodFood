package com.netcracker.service.impl;

import com.netcracker.model.User;
import com.netcracker.service.DrunkService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import com.netcracker.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.List;

@Service
public class DrunkServiceImpl implements DrunkService {

    @Autowired
    private Repository repository;

    @Autowired
    private UserService userService;

    @Override
    public void addSobOrder(String username, BigInteger sobOrdId) {
        User user = userService.getByUsername(username);
        repository.addSobOrder(user.getUserId(), sobOrdId);
    }

    @Override
    public List<BigInteger> getSobOrdersByUsername(String username) {
        List<BigInteger> allOrders;
        allOrders = repository.getSobOrdersByUsername(username);
        return allOrders;
    }
}
