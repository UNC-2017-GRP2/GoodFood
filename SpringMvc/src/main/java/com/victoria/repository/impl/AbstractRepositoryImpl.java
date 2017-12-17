package com.victoria.repository.impl;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;

public class AbstractRepositoryImpl {
    protected Connection connection;
    protected String SQL_SELECT_ID = "select id_generator()";
    protected int numericType = Types.NUMERIC;

    public AbstractRepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    protected BigInteger getObjectId() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ID)){
            while (resultSet.next()) {
                return new BigInteger(resultSet.getString("id_generator"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
