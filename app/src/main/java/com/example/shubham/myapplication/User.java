package com.example.shubham.myapplication;

public class User {

    public String email,phone;
    int roll;
    int totalAmount,grandTotal;
    public User()
    {


    }
    public User(String email, int roll, String phone,int totalAmount,int grandTotal,int expense) {
        this.email = email;
        this.roll = roll;
        this.phone = phone;
        this.totalAmount=totalAmount;
        this.grandTotal=grandTotal;

    }
}
