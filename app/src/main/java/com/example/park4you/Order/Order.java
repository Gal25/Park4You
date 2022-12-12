package com.example.park4you.Order;

import androidx.annotation.NonNull;

public class Order {

    String order_id;
    String time;
    String year,day,month;
    String OwnerPhoneNum;
    String ClientPhoneNum;


    public Order(){}

    public Order(String order_id, String time, String year, String day, String month, String OwnerPhone, String ClientPhone){
        this.ClientPhoneNum = ClientPhone;
        this.order_id = order_id;
        this.time = time;
        this.month = month;
        this.OwnerPhoneNum = OwnerPhone;
        this.day = day;
        this.year = year;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOwnerPhoneNum() {
        return OwnerPhoneNum;
    }

    public void setOwnerPhoneNum(String ownerPhoneNum) {
        OwnerPhoneNum = ownerPhoneNum;
    }

    public String getClientPhoneNum() {
        return ClientPhoneNum;
    }

    public void setClientPhoneNum(String clientPhoneNum) {
        ClientPhoneNum = clientPhoneNum;
    }
    @NonNull
    @Override
    public String toString() {
        return "Order ID:" + getOrder_id()+
                "\nHour:" + getTime() +
                "\nDate: " + getDay() +  "." + getMonth() + "." + getYear() +
                "\nOwner Phone Number: "+getOwnerPhoneNum() +
                "\nClient Phone Number:" + getClientPhoneNum();
    }




}
