package com.example.park4you.Order;

import androidx.annotation.NonNull;

public class Order {

    String city;
    String street;
    int houseNum;
    double price;
    String avHours;
    String emailOwner;
    String id;
    String emailCustomer;
    String parkingId;




    public Order(String city, String street, int houseNum, double price, String avHours, String emailOwner, String id, String emailCustomer, String parkingId) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.emailOwner = emailOwner;
        this.id = id;
        this.emailCustomer = emailCustomer;
        this.parkingId = parkingId;
    }

    public Order(){
    }

    public Order(String city,double price,String street, String avHours,String id,String emailOwner, int houseNum) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.emailOwner = emailOwner;
        this.id = id;
    }
    public String getEmailOwner() {
        return emailOwner;
    }

    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvHours() {
        return avHours;
    }

    public void setAvHours(String avHours) {
        this.avHours = avHours;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum=" + houseNum +
                ", price=" + price +
                ", avHours='" + avHours + '\'' +
                ", ownerEmail='" + emailOwner + '\'' +
                ", id='" + id + '\'' +
                ", custEmail='" + emailCustomer + '\'' +
                ", parkingId='" + parkingId + '\'' +
                '}';
    }
}
