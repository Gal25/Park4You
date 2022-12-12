package com.example.park4you.Parking;

public class Parking {
    String city;
    String street;
    int houseNum;
    double price;
    String avHours;
    long ownerPhoneNum;
    String id;
    long renterPhoneNum;


    public Parking(String city, String street, int houseNum, double price, String avHours, long ownerPhoneNum, String ID) {
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        this.price = price;
        this.avHours = avHours;
        this.ownerPhoneNum = ownerPhoneNum;
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

    public long getPhoneNum() {
        return ownerPhoneNum;
    }

    public String getid() {
        return id;
    }

//    @Override
//    public String toString() {
//        return "Parking{" +
//                "city='" + city + '\'' +
//                ", street='" + street + '\'' +
//                ", houseNum=" + houseNum +
//                ", price=" + price +
//                ", avHours='" + avHours + '\'' +
//                ", phoneNum=" + ownerPhoneNum +
//                '}';
//    }
}
