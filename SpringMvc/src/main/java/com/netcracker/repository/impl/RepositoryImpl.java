package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.Entity;
import com.netcracker.model.MapParameter;
import com.netcracker.repository.Repository;
import org.postgresql.geometric.PGpoint;
import org.springframework.cache.support.NullValue;

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

    /**
     * GET OBJECTS
     * @param objectId
     * @param objectTypeId
     * @return
     */
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
                /*PreparedStatement ps = connection.prepareStatement(Constant.SQL_SELECT_ATTR_TYPE_ID);
                ps.setLong(1, attrId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    attrTypeId = rs.getLong("ATTR_TYPE_ID");
                }*/
                attrTypeId = getAttrTypeId(attrId);
                attrs.put(attrId, attrTypeId);
                /*rs.close();
                ps.close();*/
            }
            resultSet.close();
            preparedStatement.close();
            return attrs;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long getAttrTypeId(long attrId) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Constant.SQL_SELECT_ATTR_TYPE_ID);
        ps.setLong(1, attrId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            return rs.getLong("ATTR_TYPE_ID");
        }
        return 0;
    }

    private List<MapParameter> getParameters(BigInteger objectId, Map<Long,Long> attrs) throws SQLException {
        // Map<Long, Object> parameters = new HashMap<>();
        List<MapParameter> parameters = new ArrayList<>();
        for(Map.Entry<Long, Long> attr : attrs.entrySet()){
            ResultSet resultSet = getParameter(objectId, attr.getKey());
            Long attrType = attr.getValue();
            Object attrValue = null;
            while (resultSet.next()){
                if (attrType == Constant.TEXT_ATTR_TYPE_ID || attrType == Constant.NUMBER_ATTR_TYPE_ID) {
                    attrValue = resultSet.getString("TEXT_VALUE");
                }
                /*if (attrType == Constant.NUMBER_ATTR_TYPE_ID) {
                    attrValue = resultSet.getString("NUMBER_VALUE");
                }*/
                if (attrType == Constant.REFERENCE_ATTR_TYPE_ID) {
                    attrValue = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                }
                if (attrType == Constant.DATE_ATTR_TYPE_ID) {
                    attrValue = (resultSet.getTimestamp("DATE_VALUE")!=null)?resultSet.getTimestamp("DATE_VALUE").toLocalDateTime():null;
                }
                if (attrType == Constant.ENUM_VALUE_ATTR_TYPE_ID) {
                    attrValue = getEnumValueById(resultSet.getLong("ENUM_VALUE"));
                }
                if (attrType == Constant.POINT_VALUE_ATTR_TYPE_ID) {
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


    /**
     * SAVE OBJECT
     * @param entity
     */
    private void saveEntity(Entity entity){
        try{
            BigInteger objectId = getObjectId();
            //OBJECTS
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_OBJECTS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setObject(3, new BigInteger("0"), numericType);
            preparedStatement.setLong(4, entity.getObjectTypeId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            //PARAMETERS
            for(MapParameter parameter : entity.getParameters()){
                if (parameter.getAttributeId() == Constant.TEXT_ATTR_TYPE_ID || parameter.getAttributeId() == Constant.NUMBER_ATTR_TYPE_ID){
                    saveTextParameter(objectId, parameter.getAttributeId(), (String) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.REFERENCE_ATTR_TYPE_ID){
                    saveReferenceParameter(objectId, parameter.getAttributeId(), (BigInteger) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.DATE_ATTR_TYPE_ID) {
                    saveDateParameter(objectId, parameter.getAttributeId(), (Timestamp) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.ENUM_VALUE_ATTR_TYPE_ID) {
                    saveEnumValue(objectId, parameter.getAttributeId(), getEnumIdByValue(parameter.getValue()));
                }
                if (parameter.getAttributeId() == Constant.POINT_VALUE_ATTR_TYPE_ID) {
                    if (parameter.getValue() instanceof Address){
                        Address address = (Address) parameter.getValue();
                        if (address != null){
                            savePointParameter(objectId, parameter.getAttributeId(), address.getLatitude(), address.getLongitude());
                        }else{
                            saveNullPointParameter(objectId, parameter.getAttributeId());
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private long getEnumIdByValue (Object enumValue){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_ENUM_ID_BY_ENUM_VALUE);
            preparedStatement.setString(1, (String) enumValue);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                return resultSet.getLong("ENUM_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private void saveTextParameter(BigInteger objectId, long attrId, String parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, parameter);
            preparedStatement.setDate(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    private void saveDateParameter(BigInteger objectId, long attrId, Timestamp parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, parameter);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    private void saveReferenceParameter(BigInteger objectId, long attrId, BigInteger parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, null);
            preparedStatement.setObject(5, parameter, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }


    private void saveEnumValue(BigInteger objectId, long attrId, long parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setDate(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, parameter);
            preparedStatement.setObject(7,null);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    private void savePointParameter(BigInteger objectId, long attrId, double x, double y) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS_POINT_VALUE);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setDouble(7, x);
            preparedStatement.setDouble(8, y);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    private void saveNullPointParameter(BigInteger objectId, long attrId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7, null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    /**
     *
     * @param oldEntity
     * @param newEntity
     */
     public void updateEntity (Entity oldEntity, Entity newEntity) {
        try {
            //OBJECTS
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_OBJECT_NAME);
            preparedStatement.setString(1, newEntity.getName());
            preparedStatement.setObject(2, oldEntity.getObjectId(), numericType);
            preparedStatement.executeUpdate();

            //PARAMETERS
            for(MapParameter parameter : newEntity.getParameters()){
                if (parameter.getAttributeId() == Constant.TEXT_ATTR_TYPE_ID || parameter.getAttributeId() == Constant.NUMBER_ATTR_TYPE_ID){
                    updateTextParameter(oldEntity.getObjectId(), parameter.getAttributeId(), (String) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.REFERENCE_ATTR_TYPE_ID){
                    updateReferenceParameter(oldEntity.getObjectId(), parameter.getAttributeId(), (BigInteger) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.DATE_ATTR_TYPE_ID) {
                    updateDateParameter(oldEntity.getObjectId(), parameter.getAttributeId(), (Timestamp) parameter.getValue());
                }
                if (parameter.getAttributeId() == Constant.ENUM_VALUE_ATTR_TYPE_ID) {
                    updateEnumParameter(oldEntity.getObjectId(), parameter.getAttributeId(), getEnumIdByValue(parameter.getValue()));
                }
               /* if (parameter.getAttributeId() == Constant.POINT_VALUE_ATTR_TYPE_ID) {

                }*/
            }
            updatePointListParameters(oldEntity.getObjectId(), Constant.POINT_VALUE_ATTR_TYPE_ID, newEntity.getListParametersById(Constant.POINT_VALUE_ATTR_TYPE_ID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTextParameter(BigInteger objectId, long attrId, String parameter){
        try {
            //если параметр был, то обновляем,иначе добавим
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_TEXT_PARAMETERS);
                preparedStatement.setString(1, (parameter == "")?null:parameter);
                preparedStatement.setObject(2, objectId,numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
            }else{
                saveTextParameter(objectId,attrId,parameter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateDateParameter(BigInteger objectId, long attrId, Timestamp parameter){
        try {
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_DATE_PARAMETERS);
                preparedStatement.setTimestamp(1, (parameter!=null)?new java.sql.Timestamp(parameter.getTime()):null);
                preparedStatement.setObject(2,objectId, numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
            }else{
                saveDateParameter(objectId,attrId,parameter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateReferenceParameter(BigInteger objectId, long attrId, BigInteger parameter) {
        try {
            if (checkAttribute(objectId, attrId)){
                PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_REFERENCE_PARAMETERS);
                preparedStatement.setObject(1, parameter, numericType);
                preparedStatement.setObject(2, objectId, numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
            } else {
                saveReferenceParameter(objectId, attrId, parameter);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateEnumParameter(BigInteger objectId, long attrId, long parameter){
        try {
            if (checkAttribute(objectId, attrId)){
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_ENUM_PARAMETERS);
            preparedStatement.setObject(1, parameter, numericType);
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setLong(3, attrId);
            preparedStatement.executeUpdate();
            } else {
                saveEnumValue(objectId, attrId, parameter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updatePointListParameters(BigInteger objectId, long attrId, Object parameters){

         if (parameters instanceof List){
             removeParameter(objectId, attrId);
            for(Address item : (List<Address>)parameters){
                if (item != null){
                    savePointParameter(objectId, attrId, item.getLatitude(), item.getLongitude());
                }else{
                    saveNullPointParameter(objectId, attrId);
                }
            }
         }
    }

    private void updatePointParameter(BigInteger objectId, long attrId, double x, double y){
        try {
            if (checkAttribute(objectId, attrId)){
                PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_POINT_PARAMETERS);
                preparedStatement.setDouble(1, x);
                preparedStatement.setDouble(2, y);
                preparedStatement.setObject(3, objectId, numericType);
                preparedStatement.setLong(4, attrId);
                preparedStatement.executeUpdate();
            } else {
                savePointParameter(objectId, attrId, x, y);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void updateNullPointParameter(BigInteger objectId, long attrId){
        try {
            if (checkAttribute(objectId, attrId)){
                PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_NULL_POINT_PARAMETERS);
                preparedStatement.setObject(1, null);
                preparedStatement.setObject(2, objectId, numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
            } else {
                saveNullPointParameter(objectId, attrId);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    private boolean checkAttribute(BigInteger objectId, long attrId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
        preparedStatement.setObject(1,objectId,numericType);
        preparedStatement.setLong(2,attrId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return (resultSet.next())? true:false;
    }


    /**
     * REMOVE
     * @param objectId
     * @param attrId
     */
    private void removeParameter(BigInteger objectId, long attrId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_DELETE_FROM_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
