package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;

public class AbstractRepositoryImpl{
    protected Connection connection;

    protected int numericType = Types.NUMERIC;


    public AbstractRepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    protected BigInteger getObjectId() {
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

    protected boolean checkAttribute(BigInteger objectId, long attrId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
        preparedStatement.setObject(1,objectId,numericType);
        preparedStatement.setLong(2,attrId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return (resultSet.next())? true:false;
    }

    protected ResultSet getObjectsByObjectTypeId(long objectTypeId){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECTS);
            preparedStatement.setLong(1, objectTypeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ResultSet getObjectById(BigInteger objectId){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_BY_ID);
            preparedStatement.setObject(1, objectId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String getObjNameByObjId(BigInteger objectId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_NAME_BY_OBJECT_ID);
            preparedStatement.setObject(1, objectId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("NAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    protected ResultSet getObjectIdByAttrIdAndTextVal(long attrId, String textVal){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_ID_BY_TEXT_VAL_AND_ATTR_ID);
            preparedStatement.setLong(1, attrId);
            preparedStatement.setString(2, textVal);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ResultSet getParametersByObjectId(BigInteger objectId){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_PARAMETERS_BY_OBJECT_ID);
            preparedStatement.setObject(1, objectId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected ResultSet getParameter(BigInteger objectId, long attrId) {
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

    protected ResultSet getLocStringsByObjectId(BigInteger objectId, long langId){
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_LOC_STRINGS_BY_OBJECT_ID);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, langId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected String getEnumNameById(long enumId){
        try (PreparedStatement statement = connection.prepareStatement(Constant.SQL_SELECT_ENUM_NAME_BY_ID)) {
            statement.setLong(1, enumId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                   return rs.getString("NAME");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    protected void saveObject(String name, BigInteger objectId, BigInteger parentId, long objTypeId){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_OBJECTS);
            preparedStatement.setString(1, name);
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setObject(3, parentId, numericType);
            preparedStatement.setLong(4, objTypeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void saveLocObject(BigInteger objectId, long attrId, long langId, String textValue){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_LOC);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setLong(3, langId);
            preparedStatement.setString(4, textValue);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void updateObjectName (BigInteger objectId, String newName){
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_OBJECT_NAME);
            preparedStatement.setString(1,newName);
            preparedStatement.setObject(2,objectId,numericType);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void saveTextParameter(BigInteger objectId, long attrId, String parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, parameter);
            preparedStatement.setDate(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.setObject(8,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    protected void saveDateParameter(BigInteger objectId, long attrId, Timestamp parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, parameter);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.setObject(8,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    protected void saveReferenceParameter(BigInteger objectId, long attrId, BigInteger parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, null);
            preparedStatement.setObject(5, parameter, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7,null);
            preparedStatement.setObject(8,null);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }


    protected void saveEnumValue(BigInteger objectId, long attrId, long parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setDate(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, parameter);
            preparedStatement.setObject(7,null);
            preparedStatement.setObject(8,null);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    protected void savePointParameter(BigInteger objectId, long attrId, Object x, Object y) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_INSERT_INTO_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setTimestamp(4, null);
            preparedStatement.setObject(5, 0, numericType);
            preparedStatement.setLong(6, 0);
            preparedStatement.setObject(7, x);
            preparedStatement.setObject(8, y);
            preparedStatement.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }

    protected void removeParameter(String sql, BigInteger objectId, long attrId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void removeObjectById(BigInteger objectId) throws SQLException {
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_DELETE_OBJECT);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.executeUpdate();
            removeParametersByObjId(objectId);
            connection.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
            connection.rollback();
        }
    }

    protected void removeParametersByObjId(BigInteger objectId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_DELETE_ALL_PARAMETERS_BY_OBJ_ID);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void removeLocStringsByObjId(BigInteger objectId){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_DELETE_LOC_STRINGS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    protected void removeParameter(BigInteger objectId, long attrId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_DELETE_FROM_PARAMETERS);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void removeRefParameterAndThisObject(BigInteger objectId, long attrId){
        try{
            ResultSet param = getParameter(objectId, attrId);
            BigInteger refId = null;
            while (param.next()){
                refId = new BigInteger(param.getString("REFERENCE_VALUE"));
            }
            if (refId != null){
                removeObjectById(refId);
                removeParameter(objectId, attrId);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void updateTextParameter(BigInteger objectId, long attrId, String parameter){
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

    protected void updateDateParameter(BigInteger objectId, long attrId, Timestamp parameter){
        try {
            //если парметр был, то обновляем,иначе добавим
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

    protected void updateReferenceParameter(BigInteger objectId, long attrId, BigInteger parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_REFERENCE_PARAMETERS);
            preparedStatement.setObject(1, parameter, numericType);
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setLong(3, attrId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void updateEnumParameter(BigInteger objectId, long attrId, long parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_ENUM_PARAMETERS);
            preparedStatement.setObject(1, parameter, numericType);
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setLong(3, attrId);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    protected void updateLocStrings(BigInteger objectId, long attrId, long langId, String textValue){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_UPDATE_LOC_STRING);
            preparedStatement.setString(1, textValue);
            preparedStatement.setObject(2, objectId, numericType);
            preparedStatement.setLong(3, attrId);
            preparedStatement.setLong(4, langId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean isReferenceParameterExist(BigInteger objectId, long attrId){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
            preparedStatement.setObject(1, objectId, numericType);
            preparedStatement.setLong(2, attrId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }
}
