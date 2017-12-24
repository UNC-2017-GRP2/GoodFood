package com.victoria.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
    private BigInteger orderId;
    private BigInteger userId;
    private BigDecimal orderCost;
    private String status;
    private BigInteger courierId;
    public Order(BigInteger orderId, BigInteger userId, BigDecimal orderCost, String status, BigInteger courierId) {
        this(orderId, userId, orderCost, status);
        this.courierId = courierId;
    }

    public Order(BigInteger orderId, BigInteger userId, BigDecimal orderCost, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
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

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public String getStatus() {
        return status;
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
    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
