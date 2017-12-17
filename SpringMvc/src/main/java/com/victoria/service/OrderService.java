package com.victoria.service;

import com.victoria.model.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public interface OrderService {
    BigDecimal totalOrder(ArrayList<Item> items);
    void checkout(ArrayList<Item> items, String username);
}
