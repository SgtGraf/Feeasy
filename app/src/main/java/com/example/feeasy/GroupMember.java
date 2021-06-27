package com.example.feeasy;

public class GroupMember {
    String name;
    String feeAmount;
    boolean isAdmin;

    public GroupMember(String name, boolean isAdmin) {
        this.name = name;
        this.isAdmin = isAdmin;
    }
}
