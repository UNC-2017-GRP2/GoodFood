package com.victoria.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
    private BigInteger orderId;
    private BigInteger userId;
    private BigDecimal orderCost;

    public Order(BigInteger orderId, BigInteger userId, BigDecimal orderCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }
}
