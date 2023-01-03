package com.example.park4you.Payment;

public class Payment {

    private String CardNumber;
    private String Date;
    private String CVV;
    private String Email;

    public Payment(String cardNumber, String date, String CVV, String email) {
        CardNumber = cardNumber;
        Date = date;
        this.CVV = CVV;
        Email = email;
    }


    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "CardNumber='" + CardNumber + '\'' +
                ", Date='" + Date + '\'' +
                ", CVV='" + CVV + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}
