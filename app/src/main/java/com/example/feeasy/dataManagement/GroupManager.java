package com.example.feeasy.dataManagement;

import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.FeePreset;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {

    static List<Group> groups = new ArrayList<>();
    static List<GroupMember> members = new ArrayList<>();

    static int nextGroupId = 0;
    static int nextmemberId = 0;

    public static GroupMember getLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(GroupMember member) {
        loggedIn = member;
    }

    static GroupMember loggedIn;

    public GroupManager(List<Group> groupList) {
        groups = groupList;
    }

    public static void addGroup(Group group){
        groups.add(group);
    }

    public static Group createGroup(String name){
        Group group = new Group(nextGroupId++, name, new ArrayList<GroupMember>(), new ArrayList<Fee>(), new ArrayList<FeePreset>());
        groups.add(group);
        return group;
    }

    public static void removeGroup(Group group){
        groups.remove(group);
    }

    public static List<Group> getGroups() {
        return groups;
    }

    public static Group getGroupByID(int id){
        for (Group group : groups) {
            if(group.id == id){
                return group;
            }
        }
        return null;
    }

    public static float getFeesByGroup(Group group){
        float ret = 0;
        for (Fee f:group.fees) {
            ret += f.amount;
        }
        return ret;
    }

    public static GroupMember getMemberFromGroupById(Group group, int memberId){
        for (GroupMember member:group.members) {
            if(member.id == memberId){
                return member;
            }
        }
        return null;
    }

    public static Fee createFee(String name, Group group, GroupMember member, float amount, String date){
        Fee fee = new Fee(name, group, member, amount,date);
        group.fees.add(fee);
        return fee;
    }

    public static float getFeesByMember(Group group, GroupMember member){
        float ret = 0;
        for (Fee f:group.fees) {
            if (member == f.groupMember){
                ret += f.amount;
            }
        }
        return ret;
    }

    public static FeePreset createFeePreset(String name, Group group, float amount){
        FeePreset feePreset = new FeePreset(name, group, amount);
        group.presets.add(feePreset);
        return  feePreset;
    }

    public static void addGroupMember(GroupMember groupMember, Group group){
        group.members.add(groupMember);
    }

    public static GroupMember createMember(String name){
        GroupMember member = new GroupMember(name, false, nextmemberId++);
        members.add(member);
        return member;
    }
}
