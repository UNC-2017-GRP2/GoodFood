package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
import com.netcracker.repository.Repository;
import org.postgresql.geometric.PGpoint;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositoryImpl implements Repository {

    private Connection connection;
    private int numericType = Types.NUMERIC;

    public RepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    private BigInteger getObjectId() {
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Constant.SQL_SELECT_ID)){
            while (resultSet.next()) {
                return new BigInteger(resultSet.getString("id_generator"));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Entity getEntityById(BigInteger objectId, long objectTypeId){
        try {
            Map<Long,Long> attrs = getAttrsObjectType(objectTypeId);
            List<MapParameter> params = getParameters(objectId, attrs);
            Entity entity = new Entity(objectId, objectTypeId, getObjNameByObjId(objectId), params);
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Entity getEntityByName (String name, long objectTypeId){
        BigInteger objectId = getObjectIdByName(name, objectTypeId);
        return (objectId != null)? getEntityById(objectId, objectTypeId):null;
    }

    private BigInteger getObjectIdByName(String name, long objectTypeId){
        PreparedStatement preparedStatement;
        BigInteger objectId = null;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_ID_BY_NAME_AND_TYPE);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, objectTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                objectId = new BigInteger(resultSet.getString("OBJECT_ID"));
            }
            return objectId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getObjNameByObjId(BigInteger objectId){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_NAME_BY_OBJECT_ID);
            preparedStatement.setObject(1, objectId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    public List<Entity> getEntitiesByObjectTypeId(long objectTypeId){
        List<Entity> entities = new ArrayList<>();
        Map<Long,Long> attrs = getAttrsObjectType(objectTypeId);
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECTS);
            preparedStatement.setLong(1, objectTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                BigInteger objectId = new BigInteger(resultSet.getString("OBJECT_ID"));
                String name = resultSet.getString("NAME");
                // Map<Long, Object> params = getParameters(objectId, attrs);
                List<MapParameter> params = getParameters(objectId, attrs);
                Entity entity = new Entity(objectId, objectTypeId, name, params);
                entities.add(entity);
            }
            resultSet.close();
            preparedStatement.close();
            return entities;
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    private Map<Long,Long> getAttrsObjectType(long objectTypeId){
        Map<Long,Long> attrs = new HashMap<>();  //attribute and his type_id
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_ATTRS_OBJECT_TYPE_ID);
            preparedStatement.setLong(1, objectTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                long attrId = resultSet.getLong("ATTR_ID");
                long attrTypeId = 0;
                PreparedStatement ps = connection.prepareStatement(Constant.SQL_SELECT_ATTR_TYPE_ID);
                ps.setLong(1, attrId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    attrTypeId = rs.getLong("ATTR_TYPE_ID");
                }
                attrs.put(attrId, attrTypeId);
                rs.close();
                ps.close();
            }
            resultSet.close();
            preparedStatement.close();
            return attrs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<MapParameter> getParameters(BigInteger objectId, Map<Long,Long> attrs) throws SQLException {
        // Map<Long, Object> parameters = new HashMap<>();
        List<MapParameter> parameters = new ArrayList<>();
        for(Map.Entry<Long, Long> attr : attrs.entrySet()){
            ResultSet resultSet = getParameter(objectId, attr.getKey());
            Long attr_type = attr.getValue();
            Object attrValue = null;
            while (resultSet.next()){
                if (attr_type == Constant.TEXT_ATTR_TYPE_ID || attr_type == Constant.NUMBER_ATTR_TYPE_ID) {
                    attrValue = resultSet.getString("TEXT_VALUE");
                }
                /*if (attr_type == Constant.NUMBER_ATTR_TYPE_ID) {
                    attrValue = resultSet.getString("NUMBER_VALUE");
                }*/
                if (attr_type == Constant.REFERENCE_ATTR_TYPE_ID) {
                    attrValue = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                }
                if (attr_type == Constant.DATE_ATTR_TYPE_ID) {
                    attrValue = (resultSet.getTimestamp("DATE_VALUE")!=null)?resultSet.getTimestamp("DATE_VALUE").toLocalDateTime():null;
                }
                if (attr_type == Constant.ENUM_VALUE_ATTR_TYPE_ID) {
                    attrValue = getEnumValueById(resultSet.getLong("ENUM_VALUE"));
                }
                if (attr_type == Constant.POINT_VALUE_ATTR_TYPE_ID) {
                    PGpoint address = (PGpoint)resultSet.getObject("POINT_VALUE");
                    attrValue = (address != null)?new Address(address.x, address.y):null;
                    //attrValue = new Address(address.x, address.y);
                }
                parameters.add(new MapParameter(attr.getKey(), attrValue));
            }
            // parameters.put(attr.getKey(), attrValue);
            resultSet.close();
        }
        return parameters;
    }

    private ResultSet getParameter(BigInteger objectId, long attrId){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getEnumValueById(long enumId){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_ENUM_NAME_BY_ID);
            preparedStatement.setObject(1, enumId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
