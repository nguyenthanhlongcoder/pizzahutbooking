package com.anhlang.pizzahutbooking.Object;

public class Notification {
    String name;
    String meal;
    String table;
    String time;
    Boolean read;
    String id;
    String type;

    public Notification(){

    }

    public Notification(String name, String meal, String table,String time, Boolean read, String id, String type) {
        this.name = name;
        this.meal = meal;
        this.time = time;
        this.read = read;
        this.id = id;
        this.table = table;
        this.type = type;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
