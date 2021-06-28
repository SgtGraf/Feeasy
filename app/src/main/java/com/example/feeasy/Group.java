package com.example.feeasy;

import java.util.List;

public class Group {
    int id;
    String groupName;
    List<GroupMember> members;
    List<Fee> fees;
    int totalFees;


    public Group(int id, String name, List<GroupMember> groupMembers, List<Fee> fees) {
        this.id = id;
        totalFees = 0;
        groupName = name;
        members = groupMembers;
        this.fees = fees;
    }
}
