package com.netcracker.repository;

import com.netcracker.form.MyUserAccountForm;
import com.netcracker.model.Address;
import com.netcracker.model.User;
import org.springframework.social.connect.Connection;

import javax.xml.parsers.SAXParser;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    User getUserByUsername(String username);
    User getUserById(BigInteger userId);
    User createUserAccount(Connection<?> connection);
    User registerNewUserAccount(MyUserAccountForm accountForm);
    User findByEmail(String email) throws SQLException;
    List<User> getAllUsers();
    void saveUser(User user);
    void updateUser(User oldUser, User newUser);
    void changeRole(BigInteger userId, String role);
    boolean isLoginExist(String login);
    boolean isEmailExist(String email);
    boolean isYourLoginForUpdateUser(String login, String password);
    boolean isYourEmailForUpdateUser(String email, String password);
    boolean isEqualsPassword(String password, BigInteger userId);
    void updatePassword(BigInteger userId, String password);
    void updateAddresses(BigInteger userId, List<Address> addresses);
    void removeUserById(BigInteger userId);
    BigInteger getObjectId();
}
