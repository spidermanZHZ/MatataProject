package com.example.administrator.matata_android.bean;

import java.io.Serializable;

public class LoginBean implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
