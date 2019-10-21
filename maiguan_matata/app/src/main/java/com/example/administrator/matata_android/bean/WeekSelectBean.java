package com.example.administrator.matata_android.bean;

import java.io.Serializable;

public class WeekSelectBean implements Serializable{

    private String week;
    private String number;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
