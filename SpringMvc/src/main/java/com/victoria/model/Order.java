package com.victoria.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
    private long orderId;
    private long userId;
    private BigDecimal orderCost;
    private String status;
    private long courierId;
    public Order(long orderId, long userId, BigDecimal orderCost, String status, long courierId) {
        this(orderId, userId, orderCost, status);
        this.courierId = courierId;
    }

    public Order(long orderId, long userId, BigDecimal orderCost, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getCourierId() {
        return courierId;
    }
    public long getUserId() {
        return userId;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCourierId(long userId) {
        this.courierId = userId;
    }
    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
