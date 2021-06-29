package com.example.feeasy.dataManagement;

import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import java.lang.reflect.Member;
import java.util.List;

public class GroupManager {

    static List<Group> groups;

    public GroupManager(List<Group> groupList) {
        groups = groupList;
    }

    public static void addGroup(Group group){
        groups.add(group);
    }

    public static void removeGroup(Group group){
        groups.remove(group);
    }

    public static List<Group> getGroups() {
        return groups;
    }

    public static Group getGroupPerID(int id){
        for (Group group : groups) {
            if(group.id == id){
                return group;
            }
        }
        return null;
    }

    public static float getFeesPerGroup(Group group){
        float ret = 0;
        for (Fee f:group.fees) {
            ret += f.amount;
        }
        return ret;
    }

    public static GroupMember getMemberFromGroup(Group group, int memberId){
        for (GroupMember member:group.members) {
            if(member.id == memberId){
                return member;
            }
        }
        return null;
    }

    public static float getFeesPerMember(Group group, GroupMember member){
        float ret = 0;
        for (Fee f:group.fees) {
            if (member == f.groupMember){
                ret += f.amount;
            }
        }
        return ret;
    }
}
