package com.victoria.service;

import com.victoria.model.Item;
import com.victoria.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public interface OrderService {
    BigInteger totalOrder(ArrayList<Item> items);
    void checkout(ArrayList<Item> items, String username);
    List<Order> getAllFreeOrders();
    List<Order> getAllOrdersByUser(String username);
    Order getOrderById(BigInteger orderId);
    List<Order> getAllOrdersByCourier(String username);
    void changeOrderStatus(BigInteger orderId, long statusId);
    void setCourier(BigInteger orderId, String username);
};
