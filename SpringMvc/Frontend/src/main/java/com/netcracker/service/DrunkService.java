package com.netcracker.service;

import com.netcracker.model.User;

import java.math.BigInteger;
import java.util.List;

public interface DrunkService {
    void addSobOrder(String username, BigInteger orderId);
    List<BigInteger> getSobOrdersByUsername(String username);
}
