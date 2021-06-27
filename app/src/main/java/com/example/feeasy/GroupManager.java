package com.example.feeasy;

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
        for (Group g:groups) {
            if(group == g){
                for (Fee f:g.fees) {
                    ret += f.amount;
                }
            }
        }
        return ret;
    }
}
