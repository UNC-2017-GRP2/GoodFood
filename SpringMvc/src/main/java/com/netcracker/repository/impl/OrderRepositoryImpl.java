package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.repository.ItemRepository;
import com.netcracker.repository.OrderRepository;
import org.postgresql.geometric.PGpoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends AbstractRepositoryImpl implements OrderRepository {

    @Autowired
    private ItemRepository itemRepository;

    public OrderRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public BigInteger getObjectId() {
        return super.getObjectId();
    }

    @Override
    public void checkout(Order order, long paymentType) throws SQLException {
        try{
            //order.setOrderId(getObjectId());
            connection.setAutoCommit(false); //начало транзакции, вроде бы
            //Сохраняем заказ
            saveObject("Order " + order.getOrderId(), order.getOrderId(), new BigInteger("0"), Constant.ORDER_OBJ_TYPE_ID);
            //добавляем пользователя
            saveReferenceParameter(order.getUserId(), Constant.ORDER_ATTR_ID, order.getOrderId());
            //продукты
            for (Item item : order.getOrderItems()) {
                for (int i = 0; i < item.getProductQuantity(); i++) {
                    saveReferenceParameter(order.getOrderId(), Constant.ITEM_ATTR_ID, item.getProductId());
                }
            }
            //название
            saveTextParameter(order.getOrderId(), Constant.NAME_ATTR_ID, "Order " + order.getOrderId());
            //адрес
            savePointParameter(order.getOrderId(), Constant.ADDRESS_ATTR_ID, order.getOrderAddress().getLatitude(), order.getOrderAddress().getLongitude());
            //стоимость
            saveTextParameter(order.getOrderId(), Constant.ORDERS_COST_ATTR_ID, order.getOrderCost().toString());
            //телефон
            saveTextParameter(order.getOrderId(), Constant.PHONE_NUMBER_ATTR_ID, order.getOrderPhone());
            //статус
            saveEnumValue(order.getOrderId(), Constant.STATUS_ATTR_ID, Constant.STATUS_CREATED_ENUM_ID);
            //дата создания
            saveDateParameter(order.getOrderId(),Constant.ORDER_CREATION_DATE_ATTR_ID, new java.sql.Timestamp(System.currentTimeMillis()));
            //Тип оплаты
            saveEnumValue(order.getOrderId(), Constant.ORDER_PAYMENT_TYPE_ATTR_ID, paymentType);
            //Оплачен ли заказ
            saveTextParameter(order.getOrderId(), Constant.ORDER_PAID_ATTR_ID, (order.getPaid())?"1":"0");

            connection.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> result = new ArrayList<>();
        BigInteger orderId;
        try {
            ResultSet resultSet = getObjectsByObjectTypeId(Constant.ORDER_OBJ_TYPE_ID);
            while (resultSet.next()) {
                orderId = new BigInteger(resultSet.getString("OBJECT_ID"));
                Order newOrder = getOrderById(orderId);
                result.add(newOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Order> getOrdersByUserId(BigInteger userId) {
        List<Order> result = new ArrayList<>();
        BigInteger orderId;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_REFERENCE_VAL_BY_OBJ_ID_AND_ATTR_ID);
            preparedStatement.setObject(1, userId, numericType);
            preparedStatement.setLong(2, Constant.ORDER_ATTR_ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                orderId = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                result.add(getOrderById(orderId));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public Order getOrderById(BigInteger orderId) {
        Order newOrder = null;
        BigInteger userId = new BigInteger("0");
        BigInteger courierId = new BigInteger("0");
        BigInteger orderCost = new BigInteger("0");
        String orderStatus = null;
        Address orderAddress = null;
        String orderPhone = null;
        List<Item> orderItems = new ArrayList<>();
        LocalDateTime orderCreationDate = null;
        String paymentType = null;
        Boolean isPaid = false;
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_ID_BY_REFERENCE_VAL);
            preparedStatement.setObject(1, orderId, numericType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            resultSet = getParametersByObjectId(orderId);
            //идем по всем параметрам заказа
            while (resultSet.next()) {
                long curAttrId = resultSet.getLong("ATTR_ID");
                if (curAttrId == Constant.COURIER_ATTR_ID) {
                    courierId = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                }
                if (curAttrId == Constant.ITEM_ATTR_ID) {
                    BigInteger itemId = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                    boolean flag = false;
                    for (Item item : orderItems) {
                        if (item.getProductId().compareTo(itemId) == 0) {  // они равны
                            flag = true;
                            item.setProductQuantity(item.getProductQuantity() + 1);
                            break;
                        }
                    }
                    if (!flag) {
                        orderItems.add(itemRepository.getItemById(new BigInteger(resultSet.getString("REFERENCE_VALUE"))));
                    }
                }
                if (curAttrId == Constant.STATUS_ATTR_ID) {
                    long enumValue = resultSet.getLong("ENUM_VALUE");
                    orderStatus = getEnumNameById(enumValue);
                }
                if (curAttrId == Constant.ORDERS_COST_ATTR_ID) {
                    orderCost = new BigInteger(resultSet.getString("TEXT_VALUE"));
                }
                if (curAttrId == Constant.ADDRESS_ATTR_ID){
                    if (resultSet.getObject("POINT_VALUE") != null){
                        PGpoint address = (PGpoint)resultSet.getObject("POINT_VALUE");
                        orderAddress = new Address(address.x, address.y);
                    }
                }
                if (curAttrId == Constant.PHONE_NUMBER_ATTR_ID){
                    orderPhone = resultSet.getString("TEXT_VALUE");
                }
                if (curAttrId == Constant.ORDER_CREATION_DATE_ATTR_ID) {
                    //System.out.println(resultSet.getTimestamp("DATE_VALUE").toLocalDateTime().toString());
                    orderCreationDate = resultSet.getTimestamp("DATE_VALUE").toLocalDateTime();
                }
                if (curAttrId == Constant.ORDER_PAYMENT_TYPE_ATTR_ID){
                    paymentType =  getEnumNameById(resultSet.getLong("ENUM_VALUE"));
                }
                if (curAttrId == Constant.ORDER_PAID_ATTR_ID){
                    isPaid = resultSet.getInt("TEXT_VALUE") == 1;
                }
            }
            newOrder = new Order(orderId, userId, orderCost, orderStatus, orderAddress, orderPhone, orderItems, orderCreationDate, courierId, paymentType, isPaid);
            if (resultSet != null){
                resultSet.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newOrder;
    }

    @Override
    public void changeOrderStatus(BigInteger orderId, long statusId) {
        updateEnumParameter(orderId, Constant.STATUS_ATTR_ID, statusId);
        if (statusId == Constant.STATUS_CREATED_ENUM_ID) {
            updateReferenceParameter(orderId, Constant.COURIER_ATTR_ID, new BigInteger("0"));
        }
    }

    @Override
    public void setCourier(BigInteger orderId, String username) {
        BigInteger courierId = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_ID_BY_NAME);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courierId = new BigInteger(resultSet.getString("OBJECT_ID"));
            }
            preparedStatement.close();
            resultSet.close();
            if (isReferenceParameterExist(orderId, Constant.COURIER_ATTR_ID)) {
                updateReferenceParameter(orderId, Constant.COURIER_ATTR_ID, courierId);
            } else {
                saveReferenceParameter(orderId, Constant.COURIER_ATTR_ID, courierId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        changeOrderStatus(orderId, Constant.STATUS_LINKED_WITH_COURIER_ENUM_ID);
    }

    @Override
    public void removeOrderById(BigInteger orderId){
        removeObjectById(orderId);
    }
}
