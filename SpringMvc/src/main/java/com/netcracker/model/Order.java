package com.netcracker.model;

import java.math.BigInteger;
import java.util.List;

public class Order {
    private BigInteger orderId;
    private BigInteger userId;
    private BigInteger orderCost;
    private String status;
    private String orderAddress;
    private String orderPhone;
    private BigInteger courierId;
    private List<Item> orderItems;

    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, String orderAddress, String orderPhone, List<Item> orderItems, BigInteger courierId) {
        this(orderId, userId, orderCost, status, orderAddress, orderPhone, orderItems);
        this.courierId = courierId;
    }

    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, String orderAddress, String orderPhone, List<Item> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
        this.orderItems = orderItems;
    }
/*
    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, List<Item> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
        this.orderItems = orderItems;
    }*/

    public BigInteger getOrderId() {
        return orderId;
    }

    public BigInteger getCourierId() {
        return courierId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigInteger getOrderCost() {
        return orderCost;
    }

    public String getStatus() {
        return status;
    }

    public List<Item> getOrderItems() {
        return orderItems;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setCourierId(BigInteger userId) {
        this.courierId = userId;
    }

    public void setOrderCost(BigInteger orderCost) {
        this.orderCost = orderCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderItems(List<Item> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }
}
