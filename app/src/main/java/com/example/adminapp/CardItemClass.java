package com.example.adminapp;

public class CardItemClass
{
    private String card_name;
    private int card_image;

    public CardItemClass(String card_name, int card_image) {
        this.card_image=card_image;
        this.card_name=card_name;
    }

    public String getcard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public int getCard_image() {
        return card_image;
    }

    public void setCard_image(int card_image) {
        this.card_image = card_image;
    }

}
