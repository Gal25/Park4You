package com.example.park4you.Order;

import androidx.annotation.NonNull;

import java.util.Collection;

public class Order {

    public String city;
    public String street;
    public int houseNum;
    public double price;
    public String avHours;
    public String emailOwner;
    public String id;
    public String emailCustomer;
    public String parkingId;
    public boolean parking_now;
    public String ownerID;


    public Order(String emailOwner, int houseNum, String city, double price , String emailCustomer , String avHours , String street, boolean parking_now, String ownerID) {
        this.emailOwner = emailOwner;
        this.houseNum = houseNum;
        this.city = city;
        this.price = price;
        this.id = id;
        this.emailCustomer = emailCustomer;
        this.parkingId = parkingId;
        this.avHours = avHours;
        this.street = street;
        this.parking_now = parking_now;
        this.ownerID = ownerID;
    }

    public Order(){

    }

    public Order(String email_owner, int houseNum, String city, double price, String emailCustomer, String avHours, String street,  boolean parking_now, String ownerID,String key_order, String parkingId) {
        this.emailOwner = email_owner;
        this.houseNum = houseNum;
        this.city = city;
        this.price = price;
        this.id = key_order;
        this.emailCustomer = emailCustomer;
        this.parkingId = parkingId;
        this.avHours = avHours;
        this.street = street;
        this.parking_now = parking_now;
        this.ownerID = ownerID;
    }


    public void setEmailOwner(String emailOwner) {
        this.emailOwner = emailOwner;
    }

    public void setEmailCustomer(String emailCustomer) {
        this.emailCustomer = emailCustomer;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvHours(String avHours) {
        this.avHours = avHours;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    public void setParking_now(boolean parking_now) {
        this.parking_now = parking_now;
    }
    public String getEmailOwner() {
        return emailOwner;
    }

    public String getEmailCustomer() {
        return emailCustomer;
    }


    public String getCity() {
        return this.city;
    }


    public String getStreet() {
        return street;
    }


    public int getHouseNum() {
        return houseNum;
    }


    public double getPrice() {
        return price;
    }


    public String getAvHours() {
        return avHours;
    }


    public String getId() {
        return id;
    }

    public String getParkingId() {
        return parkingId;
    }

    public boolean isParking_now() {
        return parking_now;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ownerEmail='" + emailOwner +
                ", houseNum=" + houseNum +
                ", city='" + city +
                ", price=" + price +
                ", id='" + id +
                ", custEmail='" + emailCustomer +
                ", parkingId='" + parkingId +
                ", avHours='" + avHours +
                ", street='" + street +
                '}';
    }
}
