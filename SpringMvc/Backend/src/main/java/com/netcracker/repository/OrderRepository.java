package com.netcracker.repository;

import com.netcracker.model.Item;
import com.netcracker.model.Order;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface OrderRepository {
    void checkout(Order order) throws SQLException;
    List<Order> getAllOrders();
    Order getOrderById(BigInteger orderId);
    void changeOrderStatus(BigInteger orderId, long statusId);
    void setCourier(BigInteger orderId, String username);
    List<Order> getOrdersByUserId(BigInteger userId);
    BigInteger getObjectId();
    void removeOrderById(BigInteger orderId) throws SQLException;
    void updateOrderPaid(BigInteger orderId, int isPaid);
    String getLocEnumValue(long enumId, Locale locale, String origValue);
}
