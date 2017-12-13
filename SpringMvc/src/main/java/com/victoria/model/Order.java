package com.victoria.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Order {
    private long orderId;
    private long userId;
    private BigDecimal orderCost;

    public Order(long orderId, long userId, BigDecimal orderCost) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }
}
