package com.victoria.service.impl;

import com.victoria.model.Item;
import com.victoria.repository.OrderRepository;
import com.victoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public BigDecimal totalOrder(ArrayList<Item> items) {
        BigDecimal summ = new BigDecimal(0);

        for(Item item:items){
            summ = summ.add(item.getProductCost());
        }

        return summ;
    }

    @Override
    public void checkout(ArrayList<Item> items, String username) {
        orderRepository.checkout(items, username);
    }
}
