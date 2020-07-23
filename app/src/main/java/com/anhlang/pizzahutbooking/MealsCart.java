package com.anhlang.pizzahutbooking;

import java.io.Serializable;

public class MealsCart implements Serializable {

    String name;
    String price;
    String count;
    String note;

    public  MealsCart(){

    }

    public MealsCart(String name, String price, String count, String note) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.note = note;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
