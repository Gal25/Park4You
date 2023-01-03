package com.example.park4you.Order;

import androidx.annotation.NonNull;

import com.example.park4you.Parking.Time;

import java.util.Collection;

public class Order {

    private String city;
    private String street;
    private int houseNum;
    private double price;
    private Time avHours;
    private String emailOwner;
    private String id;
    private String emailCustomer;
    private String parkingId;
    private boolean parking_now;
    private String ownerID;


    public Order(String emailOwner, int houseNum, String city, double price , String emailCustomer , Time avHours , String street, boolean parking_now, String ownerID) {
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

    public Order(String email_owner, int houseNum, String city, double price, String emailCustomer, Time avHours, String street,  boolean parking_now, String ownerID,String key_order, String parkingId) {
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

    public void setAvHours(Time avHours) {
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

    public void setOwnerID(String ownerID) {this.ownerID = ownerID;}

    public String getOwnerID() {return ownerID;}

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

    public Time getAvHours() {
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
