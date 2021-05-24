package com.example.adminapp;

public class ItemInfo {
   public String name;
    public String price,no_of_items,brand_name,selling_price,discount,booked_items_count;
    public ItemInfo()
    {

    }

    public void setBooked_items_count(String booked_items_count) {
        this.booked_items_count = booked_items_count;
    }

    public String getBooked_items_count() {
        return booked_items_count;
    }

    public void setSelling_price(String selling_price) {
        this.selling_price = selling_price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }


    public void setName(String name )
    {
        this.name=name;
    }
    public void setPrice(String price)
    {
        this.price=price;
    }
    public void setNo_of_items(String no_of_items)
    {
        this.no_of_items=no_of_items;
    }
    public String getName()
    {
        return  name;
    }
    public String getPrice()
    {
        return price;
    }
    public String getNo_of_items()
    {
        return no_of_items;
    }

    public String getDiscount() {
        return discount;
    }

    public String getSelling_price() {
        return selling_price;
    }

    public String getBrand_name() {
        return brand_name;
    }

}
