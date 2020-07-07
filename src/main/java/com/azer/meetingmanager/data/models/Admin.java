package com.azer.meetingmanager.data.models;

public class Admin extends User {

    /**
     * this function always returns true
     */
    @Override
    public boolean getIsAdmin() {
        return true;
    }

    /**
     * this function do nothing
     */
    @Override
    public void setIsAdmin(boolean isAdmin) {
    }
    
}