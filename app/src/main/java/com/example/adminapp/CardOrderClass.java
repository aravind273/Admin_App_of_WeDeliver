package com.example.adminapp;

import java.util.ArrayList;

public class CardOrderClass {
    String order_name,order_address,order_phone,order_email,order_item_details;
    long total_payable_amount;

    CardOrderClass(String order_name, String order_email, String order_phone, String order_address,String order_item_details,long total_payable_amount)
    {
        this.order_name=order_name;
        this.order_address=order_address;
        this.order_phone=order_phone;
        this.order_email=order_email;
        this.order_item_details=order_item_details;
        this.total_payable_amount=total_payable_amount;

    }

    public void setTotal_payable_amount(long total_payable_amount) {
        this.total_payable_amount = total_payable_amount;
    }

    public long getTotal_payable_amount() {
        return total_payable_amount;
    }

    public void setOrder_email(String order_email) {
        this.order_email = order_email;
    }

    public void setOrder_item_details(String order_item_details) {
        this.order_item_details = order_item_details;
    }

    public String getOrder_item_details() {
        return order_item_details;
    }

    public String getOrder_email() {
        return order_email;
    }

    public void setOrder_address(String order_address) {
        this.order_address = order_address;
    }
    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public void setOrder_phone(String order_phone) {
        this.order_phone = order_phone;
    }

    public String getOrder_address() {
        return order_address;
    }

    public String getOrder_name() {
        return order_name;
    }

    public String getOrder_phone() {
        return order_phone;
    }

}
