package com.victoria.service;

import com.victoria.model.Item;
import com.victoria.model.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public interface OrderService {
    BigDecimal totalOrder(ArrayList<Item> items);
    void checkout(ArrayList<Item> items, String username);
    List<Order> getAllFreeOrders();
    List<Order> getAllOrdersByUser(String username);
    Order getOrderById(long orderId);
    List<Order> getAllOrdersByCourier(String username);
    void changeOrderStatus(long orderId, long statusId);
    void setCourier(long orderId, String username);
}
