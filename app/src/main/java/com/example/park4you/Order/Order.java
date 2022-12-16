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

    public Order(){

    }
    public Order(String ownerEmail, int houseNum, String city, double price, String id, String custEmail, String parkingId, String avHours, String street) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.emailOwner = ownerEmail;
        this.id = id;
        this.emailCustomer = custEmail;
        this.parkingId = parkingId;
    }

    public String getCity() {
        return city;
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

    public String getOwnerEmail() {
        return emailOwner;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.emailOwner = ownerEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustEmail() {
        return emailCustomer;
    }

    public void setCustEmail(String custEmail) {
        this.emailCustomer = custEmail;
    }

    public String getParkingId() {
        return parkingId;
    }

    public void setParkingId(String parkingId) {
        this.parkingId = parkingId;
    }

    @NonNull
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
