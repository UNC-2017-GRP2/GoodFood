package com.victoria.repository.impl;

import javax.sql.DataSource;
import java.sql.*;

public class AbstractRepositoryImpl {
    protected Connection connection;
    protected String SQL_SELECT_ID = "select id_generator()";

    protected String SQL_SELECT_OBJECT_BY_ID = "select * from \"OBJECTS\" where \"OBJECT_ID\" = ?";
    protected String SQL_SELECT_PARAMETERS = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ?";
    protected String SQL_SELECT_PARAMETERS_BY_OBJ_ATTR = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
protected String SQL_SELECT_OBJECTS = "select * from \"OBJECTS\" where \"OBJECT_TYPE_ID\" = ?";

    protected String SQL_INSERT_INTO_OBJECTS = "insert into \"OBJECTS\" (\"NAME\",\"OBJECT_ID\", \"PARENT_ID\", \"OBJECT_TYPE_ID\") values(?,?,?,?)";
protected String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\") values(?,?,?,?,?,?)";


    protected String SQL_UPDATE_OBJECT = "UPDATE \"OBJECTS\" SET \"NAME\"=?, \"OBJECT_ID\"=?, \"PARENT_ID\"=?, \"OBJECT_TYPE_ID\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    protected String SQL_UPDATE_OBJECT_NAME = "UPDATE \"OBJECTS\" SET \"NAME\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    protected String SQL_UPDATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"OBJECT_ID\"=?, \"ATTR_ID\"=?, \"TEXT_VALUE\"=?, \"DATE_VALUE\"=?, \"REFERENCE_VALUE\"=?, \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    protected String SQL_UPDATE_TEXT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"TEXT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    protected String SQL_UPDATE_DATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"DATE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    protected String SQL_UPDATE_REFERENCE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"REFERENCE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    protected String SQL_UPDATE_ENUM_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";


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

    protected boolean checkAttribute(long objectId, long attrId) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
        preparedStatement.setLong(1,objectId);
        preparedStatement.setLong(2,attrId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return (resultSet.next())? true:false;
    }

}
