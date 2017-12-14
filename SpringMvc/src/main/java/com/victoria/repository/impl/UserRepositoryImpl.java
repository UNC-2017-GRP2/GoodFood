package com.victoria.repository.impl;

import com.victoria.config.Constant;
import com.victoria.model.User;
import com.victoria.repository.UserRepository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;
    public UserRepositoryImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    /*private final String TEXT_VALUE = "TEXT_VALUE";
    private final String DATE_VALUE = "DATE_VALUE";
    private final String REFERENCE_VALUE = "REFERENCE_VALUE";
    private final String ENUM_VALUE = "ENUM_VALUE";*/

    private String SQL_SELECT_LOGIN_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Логин\'";
    private String SQL_SELECT_PASSWORD_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Пароль\'";
    private String SQL_SELECT_ROLE_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Роль\'";
    private String SQL_SELECT_FIO_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'ФИО\'";
    private String SQL_SELECT_EMAIL_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'E-mail\'";
    private String SQL_SELECT_PHONE_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Телефон\'";
    private String SQL_SELECT_BIRTHDAY_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Дата рождения\'";
    private String SQL_SELECT_ADDRESS_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Адрес\'";
    private String SQL_SELECT_CARD_ATTR_ID = "select \"ATTR_ID\" from \"ATTRIBUTES\" where \"NAME\" = \'Банковская карта\'";

    private String SQL_SELECT_USER_OBJ_TYPE_ID = "select \"OBJECT_TYPE_ID\" from \"OBJECT_TYPES\" where \"NAME\" = \'Пользователь\'";

    private String SQL_SELECT_USER_ID = "select \"OBJECT_ID\" from \"PARAMETERS\" where \"ATTR_ID\" = ? and \"TEXT_VALUE\" = ? ";
    private String SQL_SELECT_PARAMETERS = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ?";
    private String SQL_SELECT_PARAMETERS_BY_OBJ_ATTR = "select * from \"PARAMETERS\" where \"OBJECT_ID\" = ? and \"ATTR_ID\" = ?";
    private String SQL_SELECT_ROLE = "select \"NAME\" from \"ENUMS\" where \"ENUM_ID\" = ?";
    private String SQL_SELECT_ROLE_ID = "select \"ENUM_ID\" from \"ENUMS\" where \"NAME\" = \'ROLE_USER\'";

    private String SQL_INSERT_INTO_OBJECTS = "insert into \"OBJECTS\" (\"NAME\",\"OBJECT_ID\", \"PARENT_ID\", \"OBJECT_TYPE_ID\") values(?,?,?,?)";
    private String SQL_INSERT_INTO_PARAMETERS = "insert into \"PARAMETERS\" (\"OBJECT_ID\",\"ATTR_ID\", \"TEXT_VALUE\", \"DATE_VALUE\", \"REFERENCE_VALUE\", \"ENUM_VALUE\") values(?,?,?,?,?,?)";

    private String SQL_UPDATE_OBJECT = "UPDATE \"OBJECTS\" SET \"NAME\"=?, \"OBJECT_ID\"=?, \"PARENT_ID\"=?, \"OBJECT_TYPE_ID\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    private String SQL_UPDATE_OBJECT_NAME = "UPDATE \"OBJECTS\" SET \"NAME\"=? WHERE \"OBJECT_ID\"=? and \"NAME\"=? ";
    private String SQL_UPDATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"OBJECT_ID\"=?, \"ATTR_ID\"=?, \"TEXT_VALUE\"=?, \"DATE_VALUE\"=?, \"REFERENCE_VALUE\"=?, \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    private String SQL_UPDATE_TEXT_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"TEXT_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    private String SQL_UPDATE_DATE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"DATE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    private String SQL_UPDATE_REFERENCE_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"REFERENCE_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";
    private String SQL_UPDATE_ENUM_PARAMETERS = "UPDATE \"PARAMETERS\" SET \"ENUM_VALUE\"=? WHERE \"OBJECT_ID\"=? and \"ATTR_ID\"=?";

    public User getUserByUsername(String username) {
        User user = null;
        long loginAttrId = getAttrId(SQL_SELECT_LOGIN_ATTR_ID);
        long passwordAttrId = getAttrId(SQL_SELECT_PASSWORD_ATTR_ID);
        long roleAttrId = getAttrId(SQL_SELECT_ROLE_ATTR_ID);
        long fioAttrId = getAttrId(SQL_SELECT_FIO_ATTR_ID);
        long emailAttrId = getAttrId(SQL_SELECT_EMAIL_ATTR_ID);
        long phoneAttrId = getAttrId(SQL_SELECT_PHONE_ATTR_ID);
        long birthdayAttrId = getAttrId(SQL_SELECT_BIRTHDAY_ATTR_ID);
        long addressAttrId = getAttrId(SQL_SELECT_ADDRESS_ATTR_ID);
        long cardAttrId = getAttrId(SQL_SELECT_CARD_ATTR_ID);

        long userId = 0;
        String password = null;
        String role = null;
        String fio = null;
        String email = null;
        String phone = null;
        Date birthday = null;
        String address = null;
        String card = null;

        if (loginAttrId != 0 && !username.equals("")){
            try{
                //достаем id юзера
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_ID);
                preparedStatement.setLong(1, loginAttrId);
                preparedStatement.setString(2, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    userId = resultSet.getLong("OBJECT_ID");
                }
                preparedStatement.close();
                resultSet.close();


                if (userId != 0 && passwordAttrId != 0 && roleAttrId != 0) {
                    preparedStatement = connection.prepareStatement(SQL_SELECT_PARAMETERS);
                    preparedStatement.setLong(1, userId);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        long curAttrId = resultSet.getLong("ATTR_ID");
                        if (curAttrId == passwordAttrId) {
                            password = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == roleAttrId) {
                            long roleValue = resultSet.getLong("ENUM_VALUE");
                            try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ROLE)) {
                                statement.setLong(1, roleValue);
                                try (ResultSet rs = statement.executeQuery()) {
                                    while (rs.next()) {
                                        role = rs.getString("NAME");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage() + "LOOOOOOOOOOOOOOOOOOOOOOOOL2");
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage() + "LOOOOOOOOL222");
                            }
                        }
                        if (curAttrId == fioAttrId){
                            fio = resultSet.getString("TEXT_VALUE");
                        }
                        if(curAttrId == emailAttrId){
                            email = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == phoneAttrId){
                            phone = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == birthdayAttrId){
                            birthday = resultSet.getDate("DATE_VALUE");
                        }
                        if(curAttrId == addressAttrId){
                            address = resultSet.getString("TEXT_VALUE");
                        }
                        if(curAttrId == cardAttrId){
                            card = resultSet.getString("TEXT_VALUE");
                        }
                    }
                    preparedStatement.close();
                    resultSet.close();
                }
                else{
                    System.out.println("userId, pass, role == 0!!!");
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
            System.out.println("loginAttId = 0 OR Username is empty!!");
        }
        return user;
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

    private long getEnumId(String sql){
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)){
            while (resultSet.next()) {
                return resultSet.getLong("ENUM_ID");
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + "LOOOOOOOOOOOOOL5");
        }
        return 0;
    }


    public List<User> getAll() {
        return null;
    }

    @Override
    public void saveUser(User user) {
        PreparedStatement preparedStatement;
        Statement statement;
        ResultSet resultSet;
        long userTypeId = 0;
        long loginAttrId = getAttrId(SQL_SELECT_LOGIN_ATTR_ID);
        long passwordAttrId = getAttrId(SQL_SELECT_PASSWORD_ATTR_ID);
        long roleAttrId = getAttrId(SQL_SELECT_ROLE_ATTR_ID);
        long roleEnumId = getEnumId(SQL_SELECT_ROLE_ID);
        long fioAttrId = getAttrId(SQL_SELECT_FIO_ATTR_ID);
        long emailAttrId = getAttrId(SQL_SELECT_EMAIL_ATTR_ID);
        long phoneAttrId = getAttrId(SQL_SELECT_PHONE_ATTR_ID);
        try{
            //Получаем OBJECT_TYPE_ID у 'Пользователь'
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_USER_OBJ_TYPE_ID);
            while (resultSet.next()){
                userTypeId = resultSet.getLong("OBJECT_TYPE_ID");
            }
            statement.close();
            resultSet.close();

            //Добавляем пользователя в бд в таблицу OBJECTS
            if (userTypeId != 0 ){
                preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_OBJECTS);
                preparedStatement.setString(1,user.getLogin());
                preparedStatement.setLong(2,24);
                preparedStatement.setInt(3,0);
                preparedStatement.setLong(4,userTypeId);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                //ДОБАВЛЯЕМ ПАРАМЕТРЫ ЮЗЕРА
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,24,fioAttrId,user.getFio());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,24,loginAttrId,user.getLogin());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,24,passwordAttrId,user.getPasswordHash());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,24,emailAttrId,user.getEmail());
                saveTextParameter(SQL_INSERT_INTO_PARAMETERS,24,phoneAttrId,user.getPhoneNumber());
                saveEnumValue(SQL_INSERT_INTO_PARAMETERS,24,roleAttrId,roleEnumId);

            }else{
                System.out.println("OBJECT_TYPE_ID НЕ НАЙДЕН");
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////Сделать проверку для логина
    @Override
    public void updateUser(User oldUser, User newUser) {
        long loginAttrId = getAttrId(SQL_SELECT_LOGIN_ATTR_ID);
        /*long passwordAttrId = getAttrId(SQL_SELECT_PASSWORD_ATTR_ID);
        long roleAttrId = getAttrId(SQL_SELECT_ROLE_ATTR_ID);
        long roleEnumId = getEnumId(SQL_SELECT_ROLE_ID);*/
        long fioAttrId = getAttrId(SQL_SELECT_FIO_ATTR_ID);
        long emailAttrId = getAttrId(SQL_SELECT_EMAIL_ATTR_ID);
        long phoneAttrId = getAttrId(SQL_SELECT_PHONE_ATTR_ID);
        long birthdayAttrId = getAttrId(SQL_SELECT_BIRTHDAY_ATTR_ID);
        long addressAttrId = getAttrId(SQL_SELECT_ADDRESS_ATTR_ID);
        long cardAttrId = getAttrId(SQL_SELECT_CARD_ATTR_ID);

        try {
            //Обновляем в objects логин пользователя
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_OBJECT_NAME);
            preparedStatement.setString(1,newUser.getLogin());
            preparedStatement.setLong(2,oldUser.getUserId());
            preparedStatement.setString(3,oldUser.getLogin());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            //обновляем параметры,если параметра не было, то добавляем его
            updateTextParameter(oldUser.getUserId(), loginAttrId, newUser.getLogin());
            updateTextParameter(oldUser.getUserId(), fioAttrId, newUser.getFio());
            updateTextParameter(oldUser.getUserId(),emailAttrId,newUser.getEmail());
            updateTextParameter(oldUser.getUserId(),phoneAttrId,newUser.getPhoneNumber());
            updateDateParameter(oldUser.getUserId(), birthdayAttrId, null/*newUser.getBirthday()*/);
            updateTextParameter(oldUser.getUserId(),addressAttrId,newUser.getAddress());
            updateTextParameter(oldUser.getUserId(),cardAttrId,newUser.getBankCard());

        }catch (Exception e){
            System.out.println(e.getMessage() + " UPDATE_OBJECT");
        }
    }

    private java.sql.Date toSqlDate(java.util.Date date ){
        return new java.sql.Date(date.getTime());
    }
    private java.util.Date toUtilDate(java.sql.Date date){
        return new java.util.Date(date.getTime());
    }

    private void updateTextParameter(long objectId, long attrId, String parameter){

        try {
            //если парметр был, то обновляем,иначе добавим
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TEXT_PARAMETERS);
                preparedStatement.setString(1, (parameter == "")?null:parameter);
                preparedStatement.setLong(2, objectId);
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
    private void updateDateParameter(long objectId, long attrId, Date parameter){
        try {
            //если парметр был, то обновляем,иначе добавим
            if (checkAttribute(objectId, attrId)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DATE_PARAMETERS);
                preparedStatement.setDate(1, parameter);
                preparedStatement.setLong(2, objectId);
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

    private boolean checkAttribute(long objectId, long attrId) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PARAMETERS_BY_OBJ_ATTR);
        preparedStatement.setLong(1,objectId);
        preparedStatement.setLong(2,attrId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return (resultSet.next())? true:false;
    }


    private void saveTextParameter(String sql, long userId, long attrId, String parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
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
    private void saveEnumValue(String sql, long userId, long attrId, long parameter){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
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
    private void saveDateParameter(String sql, long userId, long attrId, Date parameter) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, userId);
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
