package com.victoria.repository;

import com.victoria.model.Item;
import com.victoria.model.Order;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    void checkout(ArrayList<Item> items, String username);
    List<Order> getAllOrders();
    Order getOrderById(BigInteger orderId);
    void changeOrderStatus(BigInteger orderId, long statusId);
    void setCourier(BigInteger orderId, String username);
}
