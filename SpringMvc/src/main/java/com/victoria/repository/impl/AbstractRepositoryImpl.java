package com.victoria.repository.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AbstractRepositoryImpl {
    protected Connection connection;
    protected String SQL_SELECT_ID = "select id_generator()";

    public AbstractRepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    protected long getObjectId() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ID)){
            while (resultSet.next()) {
                return resultSet.getLong("id_generator");
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOL4");
        }
        return 0;
    }
}
