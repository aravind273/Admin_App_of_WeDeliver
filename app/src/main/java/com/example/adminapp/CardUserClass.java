package com.example.adminapp;

public class CardUserClass {
    public String user_name,user_address,user_phoneNumber;
    public String user_image;
    public CardUserClass()
    {

    }

    public CardUserClass(String user_name,String user_address,String user_phoneNumber,String user_image) {
        this.user_name=user_name;
        this.user_address=user_address;
        this.user_phoneNumber=user_phoneNumber;
        this.user_image=user_image;

    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_phoneNumber(String user_phoneNumber) {
        this.user_phoneNumber = user_phoneNumber;
    }

    public String getUser_phoneNumber() {
        return user_phoneNumber;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    public String getUser_address() {
        return user_address;
    }

    public String getUser_name() {
        return user_name;
    }
}
