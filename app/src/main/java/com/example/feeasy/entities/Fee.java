package com.example.feeasy.entities;

public class Fee {
    public String name;
    public Group group;
    public GroupMember groupMember;
    public float amount;
    public String date;

    public Fee(String name, Group group, GroupMember groupMember, float amount, String date) {
        this.name = name;
        this.group = group;
        this.groupMember = groupMember;
        this.amount = amount;
        this.date = date;
    }

}
