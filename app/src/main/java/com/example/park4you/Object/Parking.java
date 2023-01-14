package com.example.park4you.Object;

import java.util.Objects;

public class Parking {
    /**
     * This class represents a parking that is published by an owner. It has the usual constructor, getters, setters and a toString.
     */
    private String city;
    private String street;
    private int houseNum;
    private double price;
    private Time avHours;
    private String email;
    private String id;
    private String ownerID;



    public Parking(String city, double price, String street, Time avHours, String id, String email, int houseNum, String ownerID) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.email = email;
        this.id=id;
        this.ownerID = ownerID;
    }

    public Parking(){

    }
    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCity() {
        return city;
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

    public String getid() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        return Objects.equals(this.getid(), ((Parking) obj).getid());
    }
    @Override
    public String toString() {
        return "Parking{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNum=" + houseNum +
                ", price=" + price +
                ", avHours='" + avHours + '\'' +
                ", id='" + id + '\'' +
                ", email=" + email +
                '}';
    }
}
