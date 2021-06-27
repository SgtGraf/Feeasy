package com.example.feeasy;

import java.util.List;

public class GroupManager {

    static List<Group> groups;

    public GroupManager(List<Group> groupList) {
        groups = groupList;
    }

    public void addGroup(Group group){
        groups.add(group);
    }

    public void removeGroup(Group group){
        groups.remove(group);
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public static Group getGroupPerID(int id){
        for (Group group : groups) {
            if(group.id == id){
                return group;
            }
        }
        return null;
    }
}
