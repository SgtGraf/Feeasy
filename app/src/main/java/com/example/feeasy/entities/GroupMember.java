package com.example.feeasy.entities;

import java.util.Vector;

public class GroupMember {
    public int id;
    public String name;
    public float feeAmount;
    public boolean isAdmin;
    public Vector<Fee> fees;

    public GroupMember(String name, boolean isAdmin, int id) {
        feeAmount = 0;
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.fees = new Vector<>();
    }

    public float getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(float feeAmount) {
        this.feeAmount = feeAmount;
    }
}
