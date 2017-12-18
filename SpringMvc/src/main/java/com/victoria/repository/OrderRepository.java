package com.victoria.repository;

import com.victoria.model.Item;
import com.victoria.model.Order;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    void checkout(ArrayList<Item> items, String username);
    List<Order> getAllOrders();
    Order getOrderById(long orderId);
    void changeOrderStatus(long orderId, long statusId);
    void setCourier(long orderId, String username);
}
