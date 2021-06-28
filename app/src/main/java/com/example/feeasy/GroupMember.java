package com.example.feeasy;

public class GroupMember {
    int id;
    String name;
    float feeAmount;
    boolean isAdmin;

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
