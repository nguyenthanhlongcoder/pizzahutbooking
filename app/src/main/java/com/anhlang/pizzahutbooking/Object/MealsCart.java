package com.anhlang.pizzahutbooking.Object;

import java.io.Serializable;

public class MealsCart {

    String name;
    String price;
    String count;
    String note;
    String total;
    Boolean cache;
    String id;
    public  MealsCart(){

    }

    public MealsCart(String name, String price, String count, String note, String total, Boolean cache, String id) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.note = note;
        this.total = total;
        this.cache = cache;
        this.id = id;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Boolean getCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
