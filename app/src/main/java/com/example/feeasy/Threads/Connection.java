package com.example.feeasy.Threads;

import android.util.Log;

import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

public class Connection {

    // Sign Up
    String userName;
    String userEmail;
    String password;

    // new Fee
    String feeName;
    Group group;
    GroupMember member;
    float amount;

    // new group
    String groupName;

    // add user to group
    String userAdded;


    public void startServerThread(){
        ServerThread thread = new ServerThread();
         thread.start();
    }

    public void handleAction(ActionNames action){
        ActionThreads thread = new ActionThreads(action);
        new Thread(thread).start();
    }

    public void threadCreateFeeValues(String name, Group group, GroupMember member, float amount){
        this.feeName = name;
        this.group = group;
        this.member = member;
        this.amount = amount;
    }

    public void threadSignUpValues(String username, String email, String password){
        this.userName = username;
        this.userEmail = email;
        this.password = password;
    }

    public void threadCreateGroupValues(String name) {
        this.groupName = name;
    }

    public void threadAddUserToGroupValues(String userAdded){
        this.userAdded = userAdded;
    }

    class ServerThread extends Thread {

        @Override
        public void run() {
            try {

                // DO SERVER CONNECTION
                Thread.sleep(3000);
                Log.i("Thread", "connected!");
                // disconnect

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class ActionThreads implements Runnable{
        ActionNames action;
        public ActionThreads(ActionNames newAction){
            action = newAction;
        }

        @Override
        public void run() {
            switch (action){
                case SIGN_UP:
                    Log.i("Thread", userName + " - " + userEmail + " - " + password);
                    break;

                case CREATE_FEE:
                    Log.i("Thread", "creating fee..");
                    Log.i("Thread", feeName + " - " + group.groupName + " - " + member.name + " - " + amount);
                    Log.i("Thread", "done");
                    break;

                case SAVE_PRESET:
                    Log.i("Thread", "Preset saved");
                    break;
                case CREATE_GROUP:
                    Log.i("Thread", groupName + " created");
                    break;
                case ADD_TO_GROUP:
                    Log.i("Thread", userAdded + " added");

                case SET_FEE_STATUS:


            }
        }
    }
    public void thread(){

    }


}
