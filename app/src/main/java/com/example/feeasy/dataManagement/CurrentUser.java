package com.example.feeasy.dataManagement;

import com.example.feeasy.entities.LoggedInUser;

public class CurrentUser {

    static LoggedInUser loggedInUser;

    public CurrentUser() {
    }

    public static void setLoggedInUser(LoggedInUser loggedIn){
        loggedInUser = loggedIn;
    }

    public static LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }
}
