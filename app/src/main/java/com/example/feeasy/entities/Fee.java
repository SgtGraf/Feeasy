package com.example.feeasy.entities;

public class Fee {
    public int id;
    public String name;
    public Group group;
    public GroupMember groupMember;
    public float amount;
    public String date;
    public FeeStatus status;

    public Fee(int id, String name, Group group, GroupMember groupMember, float amount, String status) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.groupMember = groupMember;
        this.amount = amount;
        this.date = date;
        this.status = convertStringToStatus(status);
    }

    private FeeStatus convertStringToStatus(String status){
        switch (status) {
            case "declined":
                return FeeStatus.DECLINED;
            case "requested":
                return FeeStatus.REQUESTED;
            case "completed":
                return FeeStatus.COMPLETED;
            default:
                return FeeStatus.ACCEPTED;
        }
    }

}
