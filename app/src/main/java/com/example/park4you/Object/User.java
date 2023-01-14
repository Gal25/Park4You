package com.example.park4you.Object;

public class User {
    /**
     * This class represents a user and all of his information. It has the usual constructor, getters and setters.
     */
    private String email;
    private String userName;
    private String phoneNum;
    private String id;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String userName, String password, String phoneNum, String id) {
        this.email = email;
        this.userName = userName;
        this.phoneNum = phoneNum;
        this.password = password;
        this.id = id;

    }
    public User(String email, String UserName,  String phoneNum) {
        this.email = email;
        this.userName = UserName;
        this.phoneNum = phoneNum;

    }
    public User(){

    }
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
