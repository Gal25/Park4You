package com.example.park4you.Parking;

import java.util.Objects;

public class Parking {
    String city;
    String street;
    int houseNum;
    double price;
    String avHours;
//    long ownerPhoneNum;
    String Email;
    String id;


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

    public void setEmail(String email) {
        Email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Parking(String city, double price, String street, String avHours, String ID, String Email, int houseNum) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.Email = Email;
        this.id=ID;
    }

    public Parking(){

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

    public String getAvHours() {
        return avHours;
    }

//    public long getPhoneNum() {
//        return ownerPhoneNum;
//    }

    public String getid() {
        return id;
    }

    public String getEmail() {
        return Email;
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
                ", email=" + Email +
                '}';
    }
}
