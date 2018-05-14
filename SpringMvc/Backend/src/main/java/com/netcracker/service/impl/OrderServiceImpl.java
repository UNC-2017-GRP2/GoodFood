package com.netcracker.service.impl;

import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.ItemRepository;
import com.netcracker.repository.OrderRepository;
import com.netcracker.repository.UserRepository;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserService userService;

    @Override
    public BigInteger getObjectId(){
        return orderRepository.getObjectId();
    }



    @Override
    public BigInteger totalOrder(ArrayList<Item> items) {
        BigInteger sum = new BigInteger("0");
        for(Item item:items){
            BigInteger quantity = BigInteger.valueOf(item.getProductQuantity());
            sum = sum.add(item.getProductCost().multiply(quantity));
        }
        return sum;
    }

    @Override
    public void checkout(Order order) throws SQLException {
        orderRepository.checkout(order);
    }

 /*   @Override
    public void checkout(BigInteger orderId, ArrayList<Item> items, BigInteger userId, Address orderAddress, String inputPhone, long paymentType, Boolean isPaid) throws SQLException {
        Order order = new Order(orderId, userId, totalOrder(items), null, orderAddress, inputPhone, items, null, null, isPaid);
        orderRepository.checkout(order, paymentType);
    }*/


    @Override
    public List<Order> getAllOrders(Locale locale) {
        List<Order> allOrders;
        allOrders = orderRepository.getAllOrders();
        if (locale.equals(Locale.ENGLISH))
            return allOrders;
        else {
            List<Order> allLocalizedOrders = new ArrayList<>();
            List<Item> orderLocalizedItems;
            for (Order order : allOrders) {
                Order localizedOrder = new Order(order.getOrderId(),
                        order.getUserId(),
                        order.getOrderCost(),
                        order.getStatus(),
                        order.getOrderAddress(),
                        order.getOrderPhone(),
                        new ArrayList<Item>(),
                        order.getOrderCreationDate(),
                        order.getCourierId(),
                        order.getPaymentType(),
                        order.getPaid(),
                        order.getChangeFrom());
                orderLocalizedItems = localizedOrder.getOrderItems();
                for (Item item :  order.getOrderItems()) {
                    orderLocalizedItems.add(itemRepository.getLocalizedItem(item, locale));
                }
                localizedOrder.setOrderItems(orderLocalizedItems);
                allLocalizedOrders.add(localizedOrder);
            }
            return allLocalizedOrders;
        }
    }
    //public List<Order> getAllOrders() {
    //    return orderRepository.getAllOrders();
    //}

    @Override
    public List<Order> getOrdersByUsername(String username, Locale locale) {
        List<Order> allOrders;
        allOrders = orderRepository.getOrdersByUserId(userService.getByUsername(username).getUserId());
        if (locale.equals(Locale.ENGLISH))
            return allOrders;
        else {
            List<Order> allLocalizedOrders = new ArrayList<>();
            List<Item> orderLocalizedItems;
            for (Order order : allOrders) {
                Order localizedOrder = new Order(order.getOrderId(),
                        order.getUserId(),
                        order.getOrderCost(),
                        order.getStatus(),
                        order.getOrderAddress(),
                        order.getOrderPhone(),
                        new ArrayList<Item>(),
                        order.getOrderCreationDate(),
                        order.getCourierId(),
                        order.getPaymentType(),
                        order.getPaid(),
                        order.getChangeFrom());
                orderLocalizedItems = localizedOrder.getOrderItems();
                for (Item item :  order.getOrderItems()) {
                    orderLocalizedItems.add(itemRepository.getLocalizedItem(item, locale));
                }
                localizedOrder.setOrderItems(orderLocalizedItems);
                allLocalizedOrders.add(localizedOrder);
            }
            return allLocalizedOrders;
        }
    }

    @Override
    //public List<Order> getAllFreeOrders() {
    public List<Order> getAllFreeOrders(Locale locale) {
        List<Order> allOrders = getAllOrders(locale);
        List<Order> allFreeOrders = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getStatus().equals("Created") || newOrder.getStatus().equals("Without courier")) {
                allFreeOrders.add(newOrder);
            }
        }

        return allFreeOrders;
    }

    @Override
    //public List<Order> getAllOrdersByCourier(String username) {
    public List<Order> getAllOrdersByCourier(String username, Locale locale) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = getAllOrders(locale);
        List<Order> allOrdersByUser = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId)) {
                allOrdersByUser.add(newOrder);
            }
        }
        return allOrdersByUser;
    }

    @Override
    //public List<Order> getCompletedOrdersByCourier(String username) {
    public List<Order> getCompletedOrdersByCourier(String username, Locale locale) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = getAllOrders(locale);
        List<Order> completedOrders = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId) && newOrder.getStatus().equals("Delivered") || newOrder.getStatus().equals("Not delivered") || newOrder.getStatus().equals("Cancelled")) {
                completedOrders.add(newOrder);
            }
        }
        return completedOrders;
    }

    @Override
    //public List<Order> getNotCompletedOrdersByCourier(String username) {
    public List<Order> getNotCompletedOrdersByCourier(String username, Locale locale) {
        BigInteger userId = userRepository.getUserByUsername(username).getUserId();
        List<Order> allOrders = getAllOrders(locale);
        List<Order> withCourier = new ArrayList<>();
        for (Order newOrder : allOrders) {
            if (newOrder.getCourierId().equals(userId) && newOrder.getStatus().equals("Linked with courier")) {
                withCourier.add(newOrder);
            }
        }
        return withCourier;
    }


  /*  @Override
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
    }*/

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

    @Override
    public void removeOrderById(BigInteger orderId) throws SQLException {
        orderRepository.removeOrderById(orderId);
    }


}
