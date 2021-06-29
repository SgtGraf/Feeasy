package com.example.feeasy.entities;

public class Fee {
    public Group group;
    public GroupMember groupMember;
    public float amount;
    public String date;

    public Fee(Group group, GroupMember groupMember, float amount, String date) {
        this.group = group;
        this.groupMember = groupMember;
        this.amount = amount;
        this.date = date;
    }

}
