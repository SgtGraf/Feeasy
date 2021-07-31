package com.example.feeasy.entities;

import java.util.List;
import java.util.Vector;

public class Group {
    public int id;
    public String groupName;
    public List<GroupMember> members;
    public List<Fee> fees;
    public int totalFees;
    public List<FeePreset> presets;


    public Group(int id, String name, List<GroupMember> groupMembers, List<Fee> fees, List<FeePreset> presets) {
        this.id = id;
        this.groupName = name;
        this.members = groupMembers;
        this.fees = fees;
        this.presets = presets;

        totalFees = 0;
    }

    public Group(int id, String name){
        this.id = id;
        this.groupName = name;
        this.members = new Vector<>();
        this.fees = new Vector<>();
        this.presets = new Vector<>();
    }
}
