package com.example.shubham.myapplication;

public class UserData {

   public String email,phone;
   int roll;
   int totalAmount,grandTotal;

    public UserData()
    {
    }
    public UserData(String email,String phone,int roll,int totalAmount,int grandTotal)
    {

        this.email=email;
        this.roll=roll;
        this.phone=phone;
        this.totalAmount=totalAmount;
        this.grandTotal=grandTotal;

    }
}
