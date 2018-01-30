package com.netcracker.model;


import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class User {
    private BigInteger userId;
    private String fio;
    private String login;
    private String passwordHash;
    private String confirmPassword;
    private String phoneNumber;
    private Date birthday;
    private String email;
    private List<String> addresses;
    private List<String> bankCards;
    private String role;

    public User (){}


    public User(BigInteger userId, String fio, String login, String passwordHash, String confirmPassword, String phoneNumber, Date birthday, String email, List<String> addresses, List<String> bankCards, String role) {
        this.userId = userId;
        this.fio = fio;
        this.login = login;
        this.passwordHash = passwordHash;
        this.confirmPassword = confirmPassword;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.email = email;
        this.addresses = addresses;
        this.bankCards = bankCards;
        this.role = role;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public String getFio() {
        return fio;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public List<String> getBankCards() {
        return bankCards;
    }

    public String getRole() {
        return role;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }


    public void setBankCards(List<String> bankCards) {
        this.bankCards = bankCards;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fio='" + fio + '\'' +
                ", login='" + login + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    private String birthdayToString(){
        SimpleDateFormat sd = new SimpleDateFormat("dd/mm/yyyy");
        return sd.format(birthday);
    }
}
