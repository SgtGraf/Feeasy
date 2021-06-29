package com.example.feeasy.dataManagement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<CharSequence> groupId = new MutableLiveData<>();
    private MutableLiveData<CharSequence> memberId = new MutableLiveData<>();
    private MutableLiveData<Group> group = new MutableLiveData<>();
    private MutableLiveData<GroupMember> member = new MutableLiveData<>();

    public void setMember(GroupMember inputMember){
        member.setValue(inputMember);
    }

    public LiveData<GroupMember> getMember(){
        return member;
    }

    public void setGroup(Group inputGroup){
        group.setValue(inputGroup);
    }

    public LiveData<Group> getGroup(){
        return group;
    }

    public MutableLiveData<CharSequence> getMemberId() {
        return memberId;
    }

    public void setMemberId(CharSequence input) {
        memberId.setValue(input);
    }

    public void setGroupId(CharSequence input){
        groupId.setValue(input);
    }

    public LiveData<CharSequence> getGroupId() {
        return groupId;
    }
}
