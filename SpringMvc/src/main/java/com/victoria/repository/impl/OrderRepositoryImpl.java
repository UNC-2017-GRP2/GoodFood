package com.victoria.repository.impl;

import com.victoria.config.Constant;
import com.victoria.model.Item;
import com.victoria.repository.OrderRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class OrderRepositoryImpl extends AbstractRepositoryImpl implements OrderRepository {

    //private Connection connection;

    private String SQL_SELECT_OBJECT_ID = "select \"object_id\" from \"objects\" where \"name\" = ?";
    private String SQL_INSERT_INTO_OBJECTS = "insert into \"objects\" (\"name\",\"object_id\",\"parent_id\",\"object_type_id\") values(?,?,?,?)";
    private String SQL_INSERT_INTO_PARAMETERS = "insert into \"parameters\" (\"object_id\",\"attr_id\", \"text_value\", \"date_value\", \"reference_value\", \"enum_value\") values(?,?,?,?,?,?)";


    public OrderRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    @Override
    public void checkout(ArrayList<Item> items, String username) {

        long userId = 0;
        long orderId = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_OBJECT_ID);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("object_id");
            }

            orderId = getObjectId();
            //Сохраняем заказ
            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
            preparedStatement.setString(1, "Order " + orderId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.setLong(3, 0);
            preparedStatement.setLong(4, Constant.ORDER_OBJ_TYPE_ID);
            preparedStatement.executeUpdate();

            //Добавляем параметры
            if (userId != 0) {
                //добавляем пользователя
                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, Constant.ORDER_ATTR_ID);
                preparedStatement.setString(3, null);
                preparedStatement.setDate(4, null);
                preparedStatement.setLong(5, orderId);
                preparedStatement.setLong(6, 0);
                preparedStatement.executeUpdate();

                //добавляем продукты
                for (Item item : items) {
                    preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_PARAMETERS);
                    preparedStatement.setLong(1, orderId);
                    preparedStatement.setLong(2, Constant.ITEM_ATTR_ID);
                    preparedStatement.setString(3, null);
                    preparedStatement.setDate(4, null);
                    preparedStatement.setLong(5, item.getProductId());
                    preparedStatement.setLong(6, 0);
                    preparedStatement.executeUpdate();
                }
            }
            preparedStatement.close();
            resultSet.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
