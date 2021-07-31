package com.example.feeasy.entities;

public class FeePreset {
    public String name;
    public Group group;
    public float amount;

    public FeePreset(String name, Group group, float  amount) {
        this.name = name;
        this.group = group;
        this.amount = amount;
    }
}
