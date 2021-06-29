package com.example.feeasy.entities;

public class GroupMember {
    public int id;
    public String name;
    public float feeAmount;
    public boolean isAdmin;

    public GroupMember(String name, boolean isAdmin, int id) {
        feeAmount = 0;
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
    }

    public float getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(float feeAmount) {
        this.feeAmount = feeAmount;
    }
}
