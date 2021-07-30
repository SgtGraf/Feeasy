package com.example.feeasy.entities;

public class LoggedInUser {
    public int id;
    public String name;
    public String token = "";

    public LoggedInUser(int id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }
}
