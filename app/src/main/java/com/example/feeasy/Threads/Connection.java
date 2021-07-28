package com.example.feeasy.Threads;

import android.util.Log;

import com.example.feeasy.entities.ActionNames;
import com.example.feeasy.entities.Group;
import com.example.feeasy.entities.GroupMember;

import org.json.JSONObject;

public class Connection {

    public void startServerThread(){
        ServerThread thread = new ServerThread();
         thread.start();
    }

    public void handleAction(ActionNames action, JSONObject jsonObject){
        ActionThreads thread = new ActionThreads(action, jsonObject);
        new Thread(thread).start();
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
        JSONObject jsonObject = new JSONObject();
        public ActionThreads(ActionNames newAction, JSONObject jsonObject){
            action = newAction;
            this.jsonObject = jsonObject;
        }

        @Override
        public void run() {
            switch (action){
                case SIGN_UP:
                    Log.i("JSON", jsonObject.toString());
                    break;

                case CREATE_FEE:
                    Log.i("JSON", jsonObject.toString());
                    break;

                case SAVE_PRESET:
                    Log.i("JSON", jsonObject.toString());
                    break;
                case CREATE_GROUP:
                    Log.i("JSON", jsonObject.toString());
                    break;
                case ADD_TO_GROUP:
                    Log.i("JSON", jsonObject.toString());

                case SET_FEE_STATUS:


            }
        }
    }
    public void thread(){

    }


}
