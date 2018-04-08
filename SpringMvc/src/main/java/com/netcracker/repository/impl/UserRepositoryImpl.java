package com.netcracker.repository.impl;

import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.User;
import com.netcracker.repository.UserRepository;
import org.postgresql.geometric.PGpoint;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl extends AbstractRepositoryImpl implements UserRepository {

    public UserRepositoryImpl(DataSource dataSource) throws SQLException {
        super(dataSource);
    }

   /* public User getUserByUsername(String username) {
        User user = null;

        BigInteger userId = null;
        String password = null;
        String role = null;
        String fio = null;
        String email = null;
        String phone = null;
        LocalDate birthday = null;
        List<Address> addresses = new ArrayList<>();
        ResultSet resultSet = null;
        if (!username.equals("")){
            try{
                resultSet = getObjectIdByAttrIdAndTextVal(Constant.USERNAME_ATTR_ID, username);
                while (resultSet.next()) {
                    userId = new BigInteger(resultSet.getString("OBJECT_ID"));
                }

                if (userId != null && !userId.equals(0)) {
                    resultSet = getParametersByObjectId(userId);
                    while (resultSet.next()) {
                        long curAttrId = resultSet.getLong("ATTR_ID");
                        if (curAttrId == Constant.PASSWORD_HASH_ATTR_ID) {
                            password = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.USER_ROLE_ATTR_ID) {
                            long roleValue = resultSet.getLong("ENUM_VALUE");
                            try (PreparedStatement statement = connection.prepareStatement(Constant.SQL_SELECT_ENUM_NAME_BY_ID)) {
                                statement.setLong(1, roleValue);
                                try (ResultSet rs = statement.executeQuery()) {
                                    while (rs.next()) {
                                        role = rs.getString("NAME");
                                    }
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (curAttrId == Constant.FULL_NAME_ATTR_ID){
                            fio = resultSet.getString("TEXT_VALUE");
                        }
                        if(curAttrId == Constant.EMAIL_ATTR_ID){
                            email = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.PHONE_NUMBER_ATTR_ID){
                            phone = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.BIRTHDAY_ATTR_ID){
                            //birthday = new Date(resultSet.getDate("DATE_VALUE").getTime());
                            birthday = (resultSet.getTimestamp("DATE_VALUE")!=null)?resultSet.getTimestamp("DATE_VALUE").toLocalDateTime().toLocalDate():null;
                        }
                        if(curAttrId == Constant.ADDRESS_ATTR_ID){
                            PGpoint address = (PGpoint)resultSet.getObject("POINT_VALUE");
                            addresses.add( new Address(address.x, address.y));
                        }
                    }
                }
                else{
                    System.out.println("userId, pass, role == 0");
                }

                if (!password.equals("") && !role.equals("")){
                    user = new User(userId, fio, username, password, password, phone, birthday, email, addresses, role);
                }else{
                    System.out.println("pass or role is empty!");
                }
                *//*if (preparedStatement != null){
                    preparedStatement.close();
                }*//*
                if (resultSet != null){
                    resultSet.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage() + " 111");
            }
        }else{
            System.out.println("Username is empty!!");
        }
        return user;
    }*/



    @Override
    public User getUserByUsername(String username) {
        ResultSet resultSet = null;
        BigInteger userId = null;
        if (!username.equals("")) {
            try {
                resultSet = getObjectIdByAttrIdAndTextVal(Constant.USERNAME_ATTR_ID, username);
                while (resultSet.next()) {
                    userId = new BigInteger(resultSet.getString("OBJECT_ID"));
                }
                return getUserById(userId);

            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public User getUserById(BigInteger userId) {
        User user = null;
        String username = null;
        String password = null;
        String role = null;
        String fio = null;
        String email = null;
        String phone = null;
        LocalDate birthday = null;
        List<Address> addresses = new ArrayList<>();
        ResultSet resultSet = null;
            try{
                if (userId != null && !userId.equals(0)) {
                    resultSet = getParametersByObjectId(userId);
                    while (resultSet.next()) {
                        long curAttrId = resultSet.getLong("ATTR_ID");
                        if (curAttrId == Constant.PASSWORD_HASH_ATTR_ID) {
                            password = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.USER_ROLE_ATTR_ID) {
                            long roleValue = resultSet.getLong("ENUM_VALUE");
                            role = getEnumNameById(roleValue);
                        }
                        if (curAttrId == Constant.USERNAME_ATTR_ID){
                            username = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.FULL_NAME_ATTR_ID){
                            fio = resultSet.getString("TEXT_VALUE");
                        }
                        if(curAttrId == Constant.EMAIL_ATTR_ID){
                            email = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.PHONE_NUMBER_ATTR_ID){
                            phone = resultSet.getString("TEXT_VALUE");
                        }
                        if (curAttrId == Constant.BIRTHDAY_ATTR_ID){
                            birthday = (resultSet.getTimestamp("DATE_VALUE")!=null)?resultSet.getTimestamp("DATE_VALUE").toLocalDateTime().toLocalDate():null;
                        }
                        if(curAttrId == Constant.ADDRESS_ATTR_ID){
                            PGpoint address = (PGpoint)resultSet.getObject("POINT_VALUE");
                            addresses.add( new Address(address.x, address.y));
                        }
                    }
                }
                else{
                    System.out.println("userId, pass, role == 0");
                }
                if (!password.equals("") && !role.equals("")){
                    user = new User(userId, fio, username, password, password, phone, birthday, email, addresses, role);
                }else{
                    System.out.println("pass or role is empty!");
                }
            }catch (Exception e){
                System.out.println(e.getMessage() + " 111");
            }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        String username;
        try{
            ResultSet resultSet = getObjectsByObjectTypeId(Constant.USER_OBJ_TYPE_ID);
            while (resultSet.next()){
                username = resultSet.getString("NAME");
                User newUser = getUserByUsername(username);
                result.add(newUser);
            }
            if (resultSet != null){
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void saveUser(User user) {
        BigInteger userId;
        try {
            userId = getObjectId();
            if (userId != null){
                //Сохраняем юзера в объектах
                saveObject(user.getLogin(), userId, new BigInteger("0"), Constant.USER_OBJ_TYPE_ID);
                //ДОБАВЛЯЕМ ПАРАМЕТРЫ ЮЗЕРА
                saveTextParameter(userId, Constant.FULL_NAME_ATTR_ID, user.getFio());
                saveTextParameter(userId, Constant.USERNAME_ATTR_ID, user.getLogin());
                saveTextParameter(userId, Constant.PASSWORD_HASH_ATTR_ID, user.getPasswordHash());
                saveTextParameter(userId, Constant.EMAIL_ATTR_ID, user.getEmail());
                saveTextParameter(userId, Constant.PHONE_NUMBER_ATTR_ID, user.getPhoneNumber());
                saveEnumValue(userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLE_USER_ENUM_ID);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        try {
            //Обновляем в objects логин пользователя
            updateObjectName(oldUser.getUserId(), newUser.getLogin());
            //обновляем параметры,если параметра не было, то добавляем его
            updateTextParameter(oldUser.getUserId(), Constant.USERNAME_ATTR_ID, newUser.getLogin());
            updateTextParameter(oldUser.getUserId(), Constant.FULL_NAME_ATTR_ID, newUser.getFio());
            updateTextParameter(oldUser.getUserId(), Constant.EMAIL_ATTR_ID,newUser.getEmail());
            updateTextParameter(oldUser.getUserId(), Constant.PHONE_NUMBER_ATTR_ID,newUser.getPhoneNumber());
            updateDateParameter(oldUser.getUserId(), Constant.BIRTHDAY_ATTR_ID,(newUser.getBirthday()!=null)?Timestamp.valueOf(newUser.getBirthday().atStartOfDay()):null);
        }catch (Exception e){
            System.out.println(e.getMessage() + " UPDATE_OBJECT");
        }
    }

    @Override
    public void updatePassword(BigInteger userId, String password){
        try{
            updateTextParameter(userId, Constant.PASSWORD_HASH_ATTR_ID, password);
        }catch (Exception e){
            System.out.println(e.getMessage() + " UPDATE_PASSWORD");
        }
    }

    @Override
    public void updateAddresses(BigInteger userId, List<Address> addresses){
        removeParameter(Constant.SQL_DELETE_FROM_PARAMETERS,userId,Constant.ADDRESS_ATTR_ID);
        try{
            for(Address address : addresses){
                savePointParameter(userId, Constant.ADDRESS_ATTR_ID, address.getLatitude(), address.getLongitude());
            }
        }catch (Exception e){
            System.out.println(e.getMessage() + " UPDATE_ADDRESS");
        }
    }

    @Override
    public boolean isLoginExist(String login) {
        try{
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.USERNAME_ATTR_ID, login);
            if (resultSet.next()){
                resultSet.close();
                return true;
            }
            resultSet.close();
        }catch (Exception e){
            System.out.println("isLoginExist:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isEmailExist(String email) {
        try{
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.EMAIL_ATTR_ID, email);
            if (resultSet.next()){
                resultSet.close();
                return true;
            }
            if (resultSet != null){
                resultSet.close();
            }
            return false;
        }catch (Exception e){
            System.out.println("isEmailExist:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isYourLoginForUpdateUser(String login, String password){
        try{
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.USERNAME_ATTR_ID, login);
            BigInteger userId;
            while (resultSet.next()){
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
                if (isEqualsPassword(password, userId)){
                    return true;
                }
            }
            if (resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            System.out.println("isLoginExist:" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isEqualsPassword(String password, BigInteger userId) {
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_TEXT_VAL_BY_OBJ_ID_AND_ATTR_ID);
            preparedStatement.setLong(1, Constant.PASSWORD_HASH_ATTR_ID);
            preparedStatement.setObject(2, userId, numericType);
            ResultSet resultSet = preparedStatement.executeQuery();
            String getPass = "";
            while (resultSet.next()){
                getPass = resultSet.getString("TEXT_VALUE");
            }
            if(getPass.equals(password)){
                if (preparedStatement != null){
                    preparedStatement.close();
                }
                if (resultSet != null){
                    resultSet.close();
                }
                return true;
            }
            if (preparedStatement != null){
                preparedStatement.close();
            }
            if (resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            System.out.println("is equals Password: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean isYourEmailForUpdateUser(String email, String password){
        try{
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.EMAIL_ATTR_ID, email);
            BigInteger userId;
            while (resultSet.next()){
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
                if (isEqualsPassword(password, userId)){
                    return true;
                }
            }
            if (resultSet != null){
                resultSet.close();
            }
        }catch (Exception e){
            System.out.println("isLoginExist:" + e.getMessage());
        }
        return false;
    }

    @Override
    public void changeRole(BigInteger userId, String role) {
        if (role.equals("ROLE_ADMIN")) {
            updateEnumParameter(userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLE_ADMIN_ENUM_ID);
        }
        else     if (role.equals("ROLE_COURIER")) {
            updateEnumParameter(userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLE_COURIER_ENUM_ID);
        }
        else    if (role.equals("ROLE_USER")) {
            updateEnumParameter(userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLE_USER_ENUM_ID);
        }
    }
}
