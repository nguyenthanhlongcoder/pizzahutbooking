package com.anhlang.pizzahutbooking.Object;

public class Meals {

    String name;
    String price;
    String description;
    String picture;

    public  Meals(){

    }

    public Meals(String name, String price, String description, String picture) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}