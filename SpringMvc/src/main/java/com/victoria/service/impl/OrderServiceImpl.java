package com.victoria.service.impl;

import com.victoria.model.Item;
import com.victoria.model.Order;
import com.victoria.model.User;
import com.victoria.repository.OrderRepository;
import com.victoria.repository.UserRepository;
import com.victoria.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public List<Order> getAllFreeOrders() {
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> allFreeOrders = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getStatus().equals("Created") ||
                    newOrder.getStatus().equals("Without courier")) {
                allFreeOrders.add(newOrder);
            }
        }
        return allFreeOrders;
    }

    @Override
    public List<Order> getAllOrdersByCourier(String username) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> allOrdersByUser = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId)) {
                allOrdersByUser.add(newOrder);
            }
        }
        return allOrdersByUser;
    }
    @Override
    public List<Order> getAllOrdersByUser(String username) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = orderRepository.getAllOrders();
        List<Order> allOrdersByUser = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getUserId().equals(userId)) {
                allOrdersByUser.add(newOrder);
            }
        }
        return allOrdersByUser;
    }

    @Override
    public Order getOrderById(BigInteger orderId) {
        return orderRepository.getOrderById(orderId);
    }

    @Override
    public void changeOrderStatus(BigInteger orderId, long statusId) {
        orderRepository.changeOrderStatus(orderId, statusId);
    }

    @Override
    public void setCourier(BigInteger orderId, String username) {
        orderRepository.setCourier(orderId, username);
    }


}
