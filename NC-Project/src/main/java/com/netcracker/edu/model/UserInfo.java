package com.netcracker.edu.model;

public class UserInfo {
    private long id;
    private String username;
    private String passHash;
    private String role;

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getPassHash() {
        return passHash;
    }

    public String getRole() {
        return role;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserInfo() {
    }

    public UserInfo(long id, String username, String passHash, String role) {
        this.id = id;
        this.username = username;
        this.passHash = passHash;
        this.role = role;
    }
}
