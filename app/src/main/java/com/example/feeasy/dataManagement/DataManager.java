package com.example.feeasy.dataManagement;

import com.example.feeasy.entities.Fee;
import com.example.feeasy.entities.FeePreset;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;
import com.example.feeasy.entities.LoggedInUser;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

public class DataManager {

    private static DataManager manager;

    public static DataManager getDataManager(){
        if(manager == null){
            manager = new DataManager();
        }
        return manager;
    }

    private LoggedInUser loggedInUser;
    private Vector<Group> groupList;

    public DataManager() {
        groupList = new Vector<>();
    }

    public void setLoggedInUser(LoggedInUser loggedIn){
        loggedInUser = loggedIn;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public Vector<Group> getGroupList() {
        return groupList;
    }

    public void setDisplayName(String name){
        getLoggedInUser().name = name;
    }

    public Group getGroup(int id){
        for (Group group:groupList) {
            if(group.id == id){
                return group;
            }
        }
        return null;
    }

    public void loadGroupList(List<Group> groupList) {
        this.groupList.clear();
        this.groupList.addAll(groupList);
    }

    public void addGroupToGroupList(Group group){
        this.groupList.add(group);
    }

    public GroupMember getMemberOfGroup(int groupId, int memberId){
        Group group = getGroup(groupId);
        if(group != null){
            for (GroupMember member: group.members){
                if(memberId == member.id){
                    return member;
                }
            }
        }
        return null;
    }

    public void loadMembers(int groupId,List<GroupMember> members){
        Group group = getGroup(groupId);
        if(group != null){
            group.members.clear();
            group.members.addAll(members);
        }
    }

    public void addMemberToGroup(int groupId, GroupMember member){
        Group group = getGroup(groupId);
        if(group != null){
            group.members.add(member);
        }
    }

    public void loadFees(int groupId,List<Fee> fees){
        Group group = getGroup(groupId);
        if (group != null){
            group.fees.clear();
            group.fees.addAll(fees);
        }
    }

    public void loadPresets(int groupId, List<FeePreset> presets){
        Group group = getGroup(groupId);
        if(group != null){
            group.presets.clear();
            group.presets.addAll(presets);
        }
    }

    public void addFeesToMember(int groupId, int userId, List<Fee> fees){
        GroupMember member = getMemberOfGroup(groupId,userId);
        if(member != null){
            member.fees.clear();
            member.fees.addAll(fees);
        }
    }

    public String getCombinedFeeOfGroup(int groupId){
        Group group = getGroup(groupId);
        double sum = 0d;
        if(group != null){
            for (Fee fee:group.fees) {
                sum += fee.amount;
            }
        }
        return String.format("%.2f",sum) + "€";
    }

    public String getCombinedFeeOfUser(int groupId, int userId){
        Group group = getGroup(groupId);
        double sum = 0d;
        if(group != null){
            for (Fee fee: group.fees) {
                if(fee.groupMember != null && fee.groupMember.id == userId){
                    sum += fee.amount;
                }
            }
        }
        return String.format("%.2f",sum) + "€";
    }

    public void signOut(){
        loggedInUser = null;
        groupList = new Vector<Group>();
    }
}
