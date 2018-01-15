package com.victoria.model;

import java.math.BigInteger;
import java.util.List;

public class Order {
    private BigInteger orderId;
    private BigInteger userId;
    private BigInteger orderCost;
    private String status;
    private BigInteger courierId;
    private List<Item> orderItems;

    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, List<Item> orderItems, BigInteger courierId) {
        this(orderId, userId, orderCost, status, orderItems);
        this.courierId = courierId;
    }

    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, List<Item> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
        this.orderItems = orderItems;
    }

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
}
