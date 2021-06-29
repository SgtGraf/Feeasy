package com.example.feeasy.entities;

import java.util.List;

public class Group {
    public int id;
    public String groupName;
    public List<GroupMember> members;
    public List<Fee> fees;
    public int totalFees;


    public Group(int id, String name, List<GroupMember> groupMembers, List<Fee> fees) {
        this.id = id;
        totalFees = 0;
        groupName = name;
        members = groupMembers;
        this.fees = fees;
    }
}
