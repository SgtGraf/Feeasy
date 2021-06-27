package com.example.feeasy;

import java.util.List;

public class Group {
    int id;
    String groupName;
    List<GroupMember> members;
    int totalFees;

    public Group(int id, String name, List<GroupMember> groupMembers, int totalFees) {
        this.id = id;
        this.totalFees = totalFees;
        groupName = name;
        members = groupMembers;
    }
}
