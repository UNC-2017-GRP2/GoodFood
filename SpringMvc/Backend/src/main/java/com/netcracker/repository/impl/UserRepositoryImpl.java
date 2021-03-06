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
    @Override
    public BigInteger getObjectId() {
        return super.getObjectId();
    }


    @Override
    public User getUserByUsername(String username) {
        ResultSet resultSet = null;
        BigInteger userId = null;
        if (!username.equals("")) {
            try {
                resultSet = getObjectIdByAttrIdAndTextVal(Constant.NAME_ATTR_ID, username);
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
        String image = null;
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
                        if (curAttrId == Constant.NAME_ATTR_ID){
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
                        if(curAttrId == Constant.USER_IMAGE_ATTR_ID){
                            BigInteger imgValue = new BigInteger(resultSet.getString("REFERENCE_VALUE"));
                            image = getObjNameByObjId(imgValue);
                        }
                    }
                }
                else{
                    System.out.println("userId, pass, role == 0");
                }
                if (!password.equals("") && !role.equals("")){
                    user = new User(userId, fio, username, password, password, phone, birthday, email, addresses, role, image);
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
    public List<User> getAllCouriers() {
        List<User> result = new ArrayList<>();
        BigInteger userId;
        long roleValue = 0;
        try{
            ResultSet resultSet = getObjectsByObjectTypeId(Constant.USER_OBJ_TYPE_ID);
            while (resultSet.next()){
                userId = new BigInteger(resultSet.getString("OBJECT_ID"));
                if (userId != null){
                    ResultSet resultRole = getParameter(userId, Constant.USER_ROLE_ATTR_ID);
                    while (resultRole.next()){
                        roleValue = resultRole.getLong("ENUM_VALUE");
                    }
                    if (roleValue != 0 && roleValue == Constant.ROLE_COURIER_ENUM_ID){
                        User courier = getUserById(userId);
                        result.add(courier);
                    }
                }
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
    public void saveUser(User user){
        BigInteger userId;
        try {
            userId = (user.getUserId() != null)?user.getUserId() : getObjectId();
            if (userId != null){
                connection.setAutoCommit(false);
                //Сохраняем юзера в объектах
                saveObject(user.getLogin(), userId, new BigInteger("0"), Constant.USER_OBJ_TYPE_ID);
                //ДОБАВЛЯЕМ ПАРАМЕТРЫ ЮЗЕРА
                saveTextParameter(userId, Constant.FULL_NAME_ATTR_ID, user.getFio());
                saveTextParameter(userId, Constant.NAME_ATTR_ID, user.getLogin());
                saveTextParameter(userId, Constant.PASSWORD_HASH_ATTR_ID, user.getPasswordHash());
                saveTextParameter(userId, Constant.EMAIL_ATTR_ID, user.getEmail());
                saveTextParameter(userId, Constant.PHONE_NUMBER_ATTR_ID, user.getPhoneNumber());
                saveEnumValue(userId, Constant.USER_ROLE_ATTR_ID, Constant.ROLES.get(user.getRole()));
                connection.commit();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        try {
            //Обновляем в objects логин пользователя
            updateObjectName(oldUser.getUserId(), newUser.getLogin());
            //обновляем параметры,если параметра не было, то добавляем его
            updateTextParameter(oldUser.getUserId(), Constant.NAME_ATTR_ID, newUser.getLogin());
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
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.NAME_ATTR_ID, login);
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
            ResultSet resultSet = getObjectIdByAttrIdAndTextVal(Constant.NAME_ATTR_ID, login);
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

    @Override
    public void removeUserById(BigInteger userId) throws SQLException {
        removeObjectById(userId);
    }

    @Override
    public void saveUserImage(BigInteger userId, String imageName) throws SQLException {
        try{
            connection.setAutoCommit(false);
            removeRefParameterAndThisObject(userId, Constant.USER_IMAGE_ATTR_ID);
            BigInteger imageId = getObjectId();
            saveObject(imageName, imageId, new BigInteger("0"), Constant.IMAGE_OBJ_TYPE_ID);
            saveReferenceParameter(userId, Constant.USER_IMAGE_ATTR_ID, imageId);
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }
    }
    @Override
    public User findByEmail(String email) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connection.prepareStatement(Constant.SQL_SELECT_OBJECT_ID_BY_EMAIL);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        BigInteger id = new BigInteger(resultSet.getString("OBJECT_ID"));

        User user = null;
        String username = null;
        String password = null;
        String role = null;
        String fio = null;

        String phone = null;
        LocalDate birthday = null;
        List<Address> addresses = new ArrayList<>();
        resultSet = null;
        try {
            if (id != null && !id.equals(0)) {
                resultSet = getParametersByObjectId(id);
                while (resultSet.next()) {
                    long curAttrId = resultSet.getLong("ATTR_ID");
                    if (curAttrId == Constant.PASSWORD_HASH_ATTR_ID) {
                        password = resultSet.getString("TEXT_VALUE");
                    }
                    if (curAttrId == Constant.USER_ROLE_ATTR_ID) {
                        long roleValue = resultSet.getLong("ENUM_VALUE");
                        role = getEnumNameById(roleValue);
                    }
                    if (curAttrId == Constant.NAME_ATTR_ID) {
                        username = resultSet.getString("TEXT_VALUE");
                    }
                    if (curAttrId == Constant.FULL_NAME_ATTR_ID) {
                        fio = resultSet.getString("TEXT_VALUE");
                    }
                    if (curAttrId == Constant.PHONE_NUMBER_ATTR_ID) {
                        phone = resultSet.getString("TEXT_VALUE");
                    }
                    if (curAttrId == Constant.BIRTHDAY_ATTR_ID) {
                        birthday = (resultSet.getTimestamp("DATE_VALUE") != null) ? resultSet.getTimestamp("DATE_VALUE").toLocalDateTime().toLocalDate() : null;
                    }
                    if (curAttrId == Constant.ADDRESS_ATTR_ID) {
                        PGpoint address = (PGpoint) resultSet.getObject("POINT_VALUE");
                        addresses.add(new Address(address.x, address.y));
                    }
                }
            }
            if (!password.equals("") && !role.equals("")) {
                user = new User(id,fio,username,password,password,phone,birthday,email,addresses,role,null);
            } else {
                System.out.println("pass or role is empty!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " 111");
        }
        return user;
    }
}
