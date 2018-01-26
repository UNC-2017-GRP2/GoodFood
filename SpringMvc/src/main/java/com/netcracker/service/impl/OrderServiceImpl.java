package com.netcracker.service.impl;

import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.OrderRepository;
import com.netcracker.repository.UserRepository;
import com.netcracker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public BigInteger totalOrder(ArrayList<Item> items) {
        BigInteger summ = new BigInteger("0");
        for(Item item:items){
            BigInteger quantity = BigInteger.valueOf(item.getProductQuantity());
            summ = summ.add(item.getProductCost().multiply(quantity));
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
