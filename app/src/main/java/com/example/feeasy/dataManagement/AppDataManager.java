package com.example.feeasy.dataManagement;

import android.os.Handler;

public class AppDataManager {

    private static AppDataManager manager;

    public static AppDataManager getAppDataManager(){
        if(manager == null){
            manager = new AppDataManager();
        }
        return manager;
    }

    private Handler currentHandler;

    private AppDataManager(){
    }

    public Handler getCurrentHandler(){
        return currentHandler;
    }

    public void setCurrentHandler(Handler handler){
        currentHandler = handler;
    }
}
