package com.victoria.repository.impl;

import com.victoria.model.Item;
import com.victoria.repository.OrderRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OrderRepositoryImpl extends AbstractRepositoryImpl implements OrderRepository {

    //private Connection connection;

    private String SQL_SELECT_OBJECT_ID = "select \"OBJECT_ID\" from \"OBJECTS\" where \"NAME\" = ?";
    private String SQL_SELECT_ORDER_OBJ_TYPE_ID = "select \"OBJECT_TYPE_ID\" from \"OBJECT_TYPES\" where \"NAME\" = \'Order\'";
    private String SQL_INSERT_INTO_OBJECTS = "insert into \"OBJECTS\" (\"NAME\",\"OBJECT_ID\", \"PARENT_ID\", \"OBJECT_TYPE_ID\") values(?,?,?,?)";

    private String SQL_SELECT_ORDER_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Order\'";
    private String SQL_SELECT_PRODUCT_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Item\'";
    private String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\") values(?,?,?,?,?,?)";

    public OrderRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public void checkout(ArrayList<Item> items, String username) {

        long orderId = 0;
        long userId = 0;
        long orderObjectTypeId = 0;
        long orderAttrId = getAttrId(SQL_SELECT_ORDER_ATTR_ID);
        long productAttrId = getAttrId(SQL_SELECT_PRODUCT_ATTR_ID);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userId = resultSet.getLong("OBJECT_ID");
            }

            preparedStatement = connection.prepareStatement(SQL_SELECT_ORDER_OBJ_TYPE_ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                orderObjectTypeId = resultSet.getLong("OBJECT_TYPE_ID");
            }

            orderId = getObjectId();

            //Сохраняем заказ
            if (orderObjectTypeId != 0){
                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
                preparedStatement.setString(1,"Order " + orderId);
                preparedStatement.setLong(2,orderId);
                preparedStatement.setLong(3,0);
                preparedStatement.setLong(4,orderObjectTypeId);
                preparedStatement.executeUpdate();

                //Добавляем параметры
                if (userId != 0 && orderAttrId != 0){
                    //добавляем пользователя
                    preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                    preparedStatement.setLong(1,userId);
                    preparedStatement.setLong(2,orderAttrId);
                    preparedStatement.setString(3,null);
                    preparedStatement.setDate(4,null);
                    preparedStatement.setLong(5,orderId);
                    preparedStatement.setLong(6,0);
                    preparedStatement.executeUpdate();

                    //добавляем продукты
                    for(Item item:items){
                        preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                        preparedStatement.setLong(1,orderId);
                        preparedStatement.setLong(2,productAttrId);
                        preparedStatement.setString(3,null);
                        preparedStatement.setDate(4,null);
                        preparedStatement.setLong(5,item.getProductId());
                        preparedStatement.setLong(6,0);
                        preparedStatement.executeUpdate();
                    }
                }
            }
            preparedStatement.close();
            resultSet.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private long getAttrId(String sql){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()) {
                return resultSet.getLong("ATTR_ID");
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOL4");
        }
        return 0;
    }
}
