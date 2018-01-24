package com.netcracker.model;

import java.math.BigInteger;

public class OrderItem {
    private BigInteger orderId;
    private BigInteger productId;
    private int productQuantity;

    public OrderItem(BigInteger orderId, BigInteger productId, int productQuantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public BigInteger getProductId() {
        return productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public void setProductId(BigInteger productId) {
        this.productId = productId;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
