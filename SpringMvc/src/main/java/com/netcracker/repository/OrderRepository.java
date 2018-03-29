package com.netcracker.repository;

import com.netcracker.model.Item;
import com.netcracker.model.Order;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderRepository {
    void checkout(Order order) throws SQLException;
    List<Order> getAllOrders();
    Order getOrderById(BigInteger orderId);
    void changeOrderStatus(BigInteger orderId, long statusId);
    void setCourier(BigInteger orderId, String username);
    List<Order> getOrdersByUserId(BigInteger userId);
<<<<<<< HEAD
=======
   // Order getLocalizedOrder(Order order, Locale locale);
>>>>>>> bda1bd85d007419c75c896112b95933d991ce616
}
