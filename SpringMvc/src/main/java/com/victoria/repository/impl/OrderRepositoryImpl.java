package com.victoria.repository.impl;

import com.victoria.model.Item;
import com.victoria.model.Order;
import com.victoria.repository.OrderRepository;
import com.victoria.config.Constant;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends AbstractRepositoryImpl implements OrderRepository {

    //private Connection connection;

    private String SQL_SELECT_OBJECT_ID = "select \"OBJECT_ID\" from \"OBJECTS\" where \"NAME\" = ?";
    private String SQL_SELECT_ORDER_COURIER_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Order courier\'";
    private String SQL_SELECT_OBJECTS = "select * from \"OBJECTS\" where \"OBJECT_TYPE_ID\" = ?";
    private String SQL_SELECT_COST_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Order cost\'";
    private String SQL_SELECT_STATUS_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Order status\'";
    private String SQL_SELECT_STATUS = "select \"NAME\" from \"ENUMS\" where \"ENUM_ID\" = ?";
    private String SQL_SELECT_USER_ID = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"REFERENCE_VALUE\" = ?";

    public OrderRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public void checkout(ArrayList<Item> items, String username) {

        long userId = 0;
        long orderId = 0;
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userId = resultSet.getLong("OBJECT_ID");
            }

            orderId = getObjectId();

            //Сохраняем заказ

                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
                preparedStatement.setString(1,"Order " + orderId);
                preparedStatement.setLong(2,orderId);
                preparedStatement.setLong(3,0);
                preparedStatement.setLong(4,Constant.ORDER_OBJ_TYPE_ID);
                preparedStatement.executeUpdate();

                //Добавляем параметры
                if (userId != 0){
                    //добавляем пользователя
                    preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                    preparedStatement.setLong(1,userId);
                    preparedStatement.setLong(2,Constant.ORDER_ATTR_ID);
                    preparedStatement.setString(3,null);
                    preparedStatement.setDate(4,null);
                    preparedStatement.setLong(5,orderId);
                    preparedStatement.setLong(6,0);
                    preparedStatement.executeUpdate();

                    //добавляем продукты
                    for(Item item:items){
                        preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                        preparedStatement.setLong(1,orderId);
                        preparedStatement.setLong(2, Constant.ITEM_ATTR_ID);
                        preparedStatement.setString(3,null);
                        preparedStatement.setDate(4,null);
                        preparedStatement.setLong(5,item.getProductId());
                        preparedStatement.setLong(6,0);
                        preparedStatement.executeUpdate();
                }
            }
            preparedStatement.close();
            resultSet.close();
            setStatus(orderId, 803);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> result = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECTS);
            preparedStatement.setLong(1, Constant.ORDER_OBJ_TYPE_ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                long orderId = resultSet.getLong("OBJECT_ID");
                Order newOrder = getOrderById(orderId);
                result.add(newOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Order getOrderById(long orderId) {
        Order newOrder = null;
        long userId = 0;
        long courierId = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID);
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("OBJECT_ID");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOOOOOOOOOL2");
        }
        try {
            String orderStatus = null;
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_PARAMETERS);
            ps.setLong(1,orderId);
            ResultSet rs = ps.executeQuery();
            //идем по всем параметрам продукта
            while(rs.next()){
                long curAttrId = rs.getLong("ATTR_ID");
                if (curAttrId == Constant.COURIER_ATTR_ID) {
                    courierId = rs.getLong("REFERENCE_VALUE");
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
            newOrder = new Order(orderId,userId,new BigDecimal(0), orderStatus, courierId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return newOrder;
    }

    private void setStatus(long orderId, long statusId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setLong(1,orderId);
            preparedStatement.setLong(2, 417);
            preparedStatement.setString(3,null);
            preparedStatement.setDate(4,null);
            preparedStatement.setLong(5, 0);
            preparedStatement.setLong(6,statusId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void changeOrderStatus(long orderId, long statusId) {
        try {
            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_UPDATE_ENUM_PARAMETERS);
            preparedStatement1.setLong(1, statusId);
            preparedStatement1.setLong(2, orderId);
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
                preparedStatement.setLong(2, orderId);
                preparedStatement.setLong(3, 416); //order courier
                preparedStatement.executeUpdate();

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


    public void setCourier(long orderId, String username) {
        long courierId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                courierId = resultSet.getLong("OBJECT_ID");
            }
            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setLong(1,orderId);
            preparedStatement.setLong(2, Constant.ORDER_ATTR_ID);
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
