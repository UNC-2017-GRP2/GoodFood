package com.netcracker.model;

import org.springframework.cglib.core.Local;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Order {
    private BigInteger orderId;
    private BigInteger userId;
    private BigInteger orderCost;
    private String status;
    private Address orderAddress;
    private String orderPhone;
    private BigInteger courierId;
    private List<Item> orderItems;
    private LocalDateTime orderCreationDate;
    private String paymentType;
    private Boolean isPaid;

    public Order(){}

    public Order(BigInteger orderId, BigInteger userId, BigInteger orderCost, String status, Address orderAddress, String orderPhone, List<Item> orderItems, LocalDateTime orderCreationDate, String paymentType, Boolean isPaid) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
        this.orderItems = orderItems;
        this.orderCreationDate = orderCreationDate;
        this.paymentType = paymentType;
        this.isPaid = isPaid;
    }

    public Order(BigInteger orderId,
                 BigInteger userId,
                 BigInteger orderCost,
                 String status,
                 Address orderAddress,
                 String orderPhone,
                 List<Item> orderItems,
                 LocalDateTime orderCreationDate,
                 BigInteger courierId, String paymentType, Boolean isPaid) {
        this(orderId, userId, orderCost, status, orderAddress, orderPhone, orderItems, orderCreationDate, paymentType, isPaid );
        this.courierId = courierId;
    }

/*    public Order(BigInteger orderId,
                 BigInteger userId,
                 BigInteger orderCost,
                 String status,
                 Address orderAddress,
                 String orderPhone,
                 List<Item> orderItems,
                 LocalDateTime orderCreationDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderCost = orderCost;
        this.status = status;
        this.orderAddress = orderAddress;
        this.orderPhone = orderPhone;
        this.orderItems = orderItems;
        this.orderCreationDate = orderCreationDate;
    }*/
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

    public Address getOrderAddress() {
        return orderAddress;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public LocalDateTime getOrderCreationDate() {
        return orderCreationDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setOrderCreationDate(LocalDateTime orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
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

    public void setOrderAddress(Address orderAddress) {
        this.orderAddress = orderAddress;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public static final Comparator<Order> COMPARE_BY_DATE = new Comparator<Order>() {
        @Override
        public int compare(Order o1, Order o2) {
            return o2.getOrderCreationDate().compareTo(o1.getOrderCreationDate());
        }
    };
}
