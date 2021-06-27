package com.example.feeasy;

import java.util.List;

public class Group {
    int id;
    String groupName;
    List<GroupMember> members;
    int totalFees;

    public Group(int id, String name, List<GroupMember> groupMembers) {
        this.id = id;
        totalFees = 0;
        groupName = name;
        members = groupMembers;
    }
}
