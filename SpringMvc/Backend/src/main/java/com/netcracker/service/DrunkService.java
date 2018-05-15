package com.netcracker.service;

import java.math.BigInteger;
import java.util.List;

public interface DrunkService {
    void addSobOrder(String username, BigInteger sobOrdId);
    List<BigInteger> getSobOrdersByUsername(String username);
}
