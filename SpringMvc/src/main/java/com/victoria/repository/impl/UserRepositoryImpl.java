package com.victoria.repository.impl;

import com.victoria.config.Constant;
import com.victoria.model.User;
import com.victoria.repository.UserRepository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepositoryImpl implements UserRepository {


    //private Connection connection;
    public UserRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

    private String SQL_SELECT_USER_ID = "select \"object_id\" from \"parameters\" where \"attr_id\" = ? and \"text_value\" = ? ";
    private String SQL_SELECT_PARAMETERS = "select * from \"parameters\" where \"object_id\" = ?";
    private String SQL_SELECT_PARAMETERS_BY_OBJ_ATTR = "select * from \"parameters\" where \"object_id\" = ? and \"attr_id\" = ?";
    private String SQL_SELECT_ROLE = "select \"name\" from \"enums\" where \"enum_id\" = ?";
    private String SQL_INSERT_INTO_OBJECTS = "insert into \"objects\" (\"name\",\"object_id\", \"parent_id\", \"object_type_id\") values(?,?,?,?)";
    private String SQL_INSERT_INTO_PARAMETERS = "insert into \"parameters\" (\"object_id\",\"attr_id\", \"text_value\", \"date_value\", \"reference_value\", \"enum_value\") values(?,?,?,?,?,?)";
    private String SQL_UPDATE_OBJECT = "UPDATE \"objects\" SET \"name\"=?, \"object_id\"=?, \"parent_id\"=?, \"object_type_id\"=? WHERE \"object_id\"=? and \"name\"=? ";
    private String SQL_UPDATE_OBJECT_NAME = "UPDATE \"objects\" SET \"name\"=? WHERE \"object_id\"=? and \"name\"=? ";
    private String SQL_UPDATE_PARAMETERS = "UPDATE \"parameters\" SET \"object_id\"=?, \"attr_id\"=?, \"text_value\"=?, \"date_value\"=?, \"reference_value\"=?, \"enum_value\"=? WHERE \"object_id\"=? and \"attr_id\"=?";
    private String SQL_UPDATE_TEXT_PARAMETERS = "UPDATE \"parameters\" SET \"text_value\"=? WHERE \"object_id\"=? and \"attr_id\"=?";
    private String SQL_UPDATE_DATE_PARAMETERS = "UPDATE \"parameters\" SET \"text_value\"=? WHERE \"object_id\"=? and \"attr_id\"=?";

    public User getUserByUsername(String username) {
        User user = null;

        BigInteger userId = null;
        String password = null;
        String role = null;
        String fio = null;
        String email = null;
        String phone = null;
        Date birthday = null;
        String address = null;
        String card = null;

        if (!username.equals("")){
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID);
                preparedStatement.setLong(1, Constant.USERNAME_ATTR_ID);
                preparedStatement.setString(2, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    userId = new BigInteger(resultSet.getString("object_id"));
                }
                preparedStatement.close();
                resultSet.close();


                if (userId != null && !userId.equals(0)) {
                    preparedStatement = connection.prepareStatement(SQL_SELECT_PARAMETERS);
                    preparedStatement.setObject(1, userId, numericType);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        long curAttrId = resultSet.getLong("attr_id");
                        if (curAttrId == Constant.PASSWORD_HASH_ATTR_ID) {
                            password = resultSet.getString("text_value");
                        }
                        if (curAttrId == Constant.USER_ROLE_ATTR_ID) {
                            long roleValue = resultSet.getLong("enum_value");
                            try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROLE)) {
                                statement.setLong(1, roleValue);
                                try (ResultSet rs = statement.executeQuery()) {
                                    while (rs.next()) {
                                        role = rs.getString("name");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (curAttrId == Constant.FULL_NAME_ATTR_ID){
                            fio = resultSet.getString("text_value");
                        }
                        if(curAttrId == Constant.EMAIL_ATTR_ID){
                            email = resultSet.getString("text_value");
                        }
                        if (curAttrId == Constant.PHONE_NUMBER_ATTR_ID){
                            phone = resultSet.getString("text_value");
                        }
                        if (curAttrId == Constant.BIRTHDAY_ATTR_ID){
                            birthday = resultSet.getDate("date_value");
                        }
                        if(curAttrId == Constant.ADDRESS_ATTR_ID){
                            address = resultSet.getString("text_value");
                        }
                        if(curAttrId == Constant.BANK_CARD_NUMBER_ATTR_ID){
                            card = resultSet.getString("text_value");
                        }
                    }
                    preparedStatement.close();
                    resultSet.close();
                }
                else{
                    System.out.println("userId, pass, role == 0");
                }

                if (!password.equals("") && !role.equals("")){
                    user = new User(userId, fio, username, password, phone,birthday, email,address, card, role);
                }else{
                    System.out.println("pass or role is empty!");
                }
            }catch (Exception e){
                System.out.println(e.getMessage() + " 111");
            }
        }else{
            System.out.println("Username is empty!!");
        }
        return user;
    }

    public List<User> getAll() {
        return null;
    }

    @Override
    public void saveUser(User user) {
        PreparedStatement preparedStatement;
        BigInteger userId;
        try {
            userId = getObjectId();
            if (userId != null){
                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setObject(2, userId, numericType);
                preparedStatement.setObject(3, 0, numericType);
                preparedStatement.setLong(4, Constant.USER_OBJ_TYPE_ID);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                //ДОБАВЛЯЕМ ПАРАМЕТРЫ ЮЗЕРА
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS, userId, Constant.FULL_NAME_ATTR_ID, user.getFio());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS, userId, Constant.USERNAME_ATTR_ID, user.getLogin());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS, userId, Constant.PASSWORD_HASH_ATTR_ID, user.getPasswordHash());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS, userId, Constant.EMAIL_ATTR_ID, user.getEmail());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS, userId, Constant.PHONE_NUMBER_ATTR_ID, user.getPhoneNumber());
                saveEnumValue(SQL_INSERT_INTO_PARAMETERS, userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLE_USER_ENUM_ID);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////Сделать проверку для логина
    @Override
    public void updateUser(User oldUser, User newUser) {
        try {

            //Обновляем в objects логин пользователя
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_OBJECT_NAME);
            preparedStatement.setString(1,newUser.getLogin());
            preparedStatement.setObject(2,oldUser.getUserId(),numericType);
            preparedStatement.setString(3,oldUser.getLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            //обновляем параметры,если параметра не было, то добавляем его
            updateTextParameter(oldUser.getUserId(),Constant.USERNAME_ATTR_ID, newUser.getLogin());
            updateTextParameter(oldUser.getUserId(),Constant.FULL_NAME_ATTR_ID, newUser.getFio());
            updateTextParameter(oldUser.getUserId(),Constant.EMAIL_ATTR_ID,newUser.getEmail());
            updateTextParameter(oldUser.getUserId(),Constant.PHONE_NUMBER_ATTR_ID,newUser.getPhoneNumber());
            updateDateParameter(oldUser.getUserId(), Constant.BIRTHDAY_ATTR_ID, null);
            updateTextParameter(oldUser.getUserId(),Constant.ADDRESS_ATTR_ID,newUser.getAddress());
            updateTextParameter(oldUser.getUserId(),Constant.BANK_CARD_NUMBER_ATTR_ID,newUser.getBankCard());

        }catch (Exception e){
            System.out.println(e.getMessage() + " UPDATE_OBJECT");
        }
    }

    private void updateTextParameter(BigInteger objectId, long attrId, String parameter){

        try {
            //если парметр был, то обновляем,иначе добавим
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TEXT_PARAMETERS);
                preparedStatement.setString(1, (parameter == "")?null:parameter);
                preparedStatement.setObject(2, objectId,numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }else{
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,objectId,attrId,parameter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void updateDateParameter(BigInteger objectId, long attrId, Date parameter){
        try {
            //если парметр был, то обновляем,иначе добавим
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DATE_PARAMETERS);
                preparedStatement.setDate(1, parameter);
                preparedStatement.setObject(2,objectId, numericType);
                preparedStatement.setLong(3, attrId);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }else{
                saveDateParameter(SQL_INSERT_INTO_PARAMETERS,objectId,attrId,parameter);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private boolean checkAttribute(BigInteger objectId, long attrId) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
        preparedStatement.setObject(1,objectId,numericType);
        preparedStatement.setLong(2,attrId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return (resultSet.next())? true:false;
    }


    private void saveTextParameter(String sql, BigInteger userId, long attrId, String parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, userId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, parameter);
            preparedStatement.setDate(4, null);
            preparedStatement.setLong(5, 0);
            preparedStatement.setLong(6, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }
    private void saveEnumValue(String sql, BigInteger userId, long attrId, long parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, userId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setDate(4, null);
            preparedStatement.setLong(5, 0);
            preparedStatement.setLong(6, parameter);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }
    private void saveDateParameter(String sql, BigInteger userId, long attrId, Date parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, userId, numericType);
            preparedStatement.setLong(2, attrId);
            preparedStatement.setString(3, null);
            preparedStatement.setDate(4, parameter);
            preparedStatement.setLong(5, 0);
            preparedStatement.setLong(6, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e){
            System.out.println(e.getMessage() + " SAVE_PARAMETER");
        }
    }
}
