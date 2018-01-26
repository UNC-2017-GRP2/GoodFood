package com.victoria.repository.impl;

import com.victoria.config.Constant;
import com.victoria.model.Item;
import com.victoria.model.Order;
import com.victoria.repository.ItemRepository;
import com.victoria.repository.OrderRepository;
import com.victoria.config.Constant;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends AbstractRepositoryImpl implements OrderRepository {

    @Autowired
    private ItemRepository itemRepository;

    private String SQL_SELECT_OBJECT_ID = "select \"OBJECT_ID\" from \"OBJECTS\" where \"NAME\" = ?";
    private String SQL_SELECT_OBJECTS = "select * from \"OBJECTS\" where \"OBJECT_TYPE_ID\" = ?";
    private String SQL_SELECT_STATUS = "select \"NAME\" from \"ENUMS\" where \"ENUM_ID\" = ?";
    private String SQL_SELECT_USER_ID = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"REFERENCE_VALUE\" = ?";

    public OrderRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public void checkout(ArrayList<Item> items, String username) {

        BigInteger userId = null;
        BigInteger orderId;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
            }

            orderId = getObjectId();

            if (orderId != null){
                //Сохраняем заказ
                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
                preparedStatement.setString(1, "Order " + orderId);
                preparedStatement.setObject(2, orderId, numericType);
                preparedStatement.setLong(3, 0);
                preparedStatement.setLong(4, Constant.ORDER_OBJ_TYPE_ID);
                preparedStatement.executeUpdate();

                //Добавляем параметры
                if (userId != null) {
                    //добавляем пользователя
                    preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                    preparedStatement.setObject(1, userId, numericType);
                    preparedStatement.setLong(2, Constant.ORDER_ATTR_ID);
                    preparedStatement.setString(3, null);
                    preparedStatement.setDate(4, null);
                    preparedStatement.setObject(5, orderId, numericType);
                    preparedStatement.setLong(6, 0);
                    preparedStatement.executeUpdate();

                    //добавляем продукты
                    for (Item item : items) {
                        for (int i=0;i<item.getProductQuantity();i++){
                            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                            preparedStatement.setObject(1, orderId, numericType);
                            preparedStatement.setLong(2, Constant.ITEM_ATTR_ID);
                            preparedStatement.setString(3, null);
                            preparedStatement.setDate(4, null);
                            preparedStatement.setObject(5, item.getProductId(), numericType);
                            preparedStatement.setLong(6, 0);
                            preparedStatement.executeUpdate();
                        }
                    }
                }
            }
            preparedStatement.close();
            resultSet.close();
            setStatus(orderId, 803);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> result = new ArrayList<>();
        BigInteger orderId;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECTS);
            preparedStatement.setLong(1, Constant.ORDER_OBJ_TYPE_ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
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
    public Order getOrderById(BigInteger orderId) {
        Order newOrder = null;
        BigInteger userId = new BigInteger("0");
        BigInteger courierId = new BigInteger("0");
        BigInteger orderCost = new BigInteger("0");
        String orderStatus = null;
        List<Item> orderItems = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID);
            preparedStatement.setObject(1, orderId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOOOOOOOOOL2");
        }
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_PARAMETERS);
            ps.setObject(1,orderId, numericType);
            ResultSet rs = ps.executeQuery();
            //идем по всем параметрам продукта
            while(rs.next()){
                long curAttrId = rs.getLong("ATTR_ID");
                if (curAttrId == Constant.COURIER_ATTR_ID) {
                    courierId = new BigInteger(rs.getString("REFERENCE_VALUE"));
                }
                if (curAttrId == Constant.ITEM_ATTR_ID) {
                    orderItems.add(itemRepository.getItemById(new BigInteger(rs.getString("REFERENCE_VALUE"))));
                }
                if (curAttrId == Constant.STATUS_ATTR_ID) {
                    long enumValue = rs.getLong("ENUM_VALUE");
                    try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_STATUS)) {
                        statement.setLong(1, enumValue);
                        try (ResultSet resultSet1 = statement.executeQuery()) {
                            while (resultSet1.next()) {
                                orderStatus = resultSet1.getString("NAME");
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOOOOOOOOOL2");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + "LOOOOOOOOL222");
                    }
                }
            }
            for(Item item:orderItems){
                orderCost = orderCost.add(item.getProductCost());
            }
            newOrder = new Order(orderId,userId,orderCost, orderStatus, orderItems, courierId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return newOrder;
    }

    private void setStatus(BigInteger orderId, long statusId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1,orderId, numericType);
            preparedStatement.setLong(2, 417);
            preparedStatement.setString(3,null);
            preparedStatement.setDate(4,null);
            preparedStatement.setLong(5, 0);
            preparedStatement.setLong(6, statusId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void changeOrderStatus(BigInteger orderId, long statusId) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_UPDATE_ENUM_PARAMETERS);
            preparedStatement1.setObject(1, statusId, numericType);
            preparedStatement1.setObject(2, orderId, numericType);
            preparedStatement1.setLong(3, 417);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (statusId == 803) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_REFERENCE_PARAMETERS);
                preparedStatement.setLong(1, 0);
                preparedStatement.setObject(2, orderId, numericType);
                preparedStatement.setLong(3, 416); //order courier
                preparedStatement.executeUpdate();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void setCourier(BigInteger orderId, String username) {
        long courierId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                courierId = resultSet.getLong("OBJECT_ID");
            }
            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1,orderId, numericType);
            preparedStatement.setLong(2, Constant.COURIER_ATTR_ID);
            preparedStatement.setString(3,null);
            preparedStatement.setDate(4,null);
            preparedStatement.setLong(5, courierId);
            preparedStatement.setLong(6,0);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        changeOrderStatus(orderId, 805);
    }
}
