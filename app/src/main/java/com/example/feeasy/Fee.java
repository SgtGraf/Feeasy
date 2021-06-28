package com.example.feeasy;

import java.util.Date;

public class Fee {
    Group group;
    GroupMember groupMember;
    float amount;
    String date;

    public Fee(Group group, GroupMember groupMember, float amount, String date) {
        this.group = group;
        this.groupMember = groupMember;
        this.amount = amount;
        this.date = date;
    }

}
