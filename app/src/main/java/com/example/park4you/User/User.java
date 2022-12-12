package com.example.park4you.User;

import androidx.annotation.NonNull;

import com.example.park4you.Order.Order;

import java.util.List;

public class User {
    String email;
    String UserName;
    String phoneNum;
    private String password;
    public List<Order> Orders;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String UserName, String password, String phoneNum) {
        this.email = email;
        this.UserName = UserName;
        this.phoneNum = phoneNum;
        this.password = password;

    }
    public User(String email, String UserName,  String phoneNum) {
        this.email = email;
        this.UserName = UserName;
        this.phoneNum = phoneNum;

    }
    public User(){
//        email = "";
//        UserName= "";
//        phoneNum = "";
    }

    public void addOrder(Order o) {
        this.Orders.add(o);
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
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder ans= new StringBuilder();
        for (Order o : Orders) {
            ans.append(o.toString());
        }
        return ans.toString();

    }

}
