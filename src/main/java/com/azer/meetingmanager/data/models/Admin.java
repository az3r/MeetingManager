package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin(){

    }
    
    public Admin(String userName, String userEmail, Account account) {
        super(userName, userEmail, account);
    }

    @Override
    public String toString() {
        return "Admin [userId=" + getUserId() + ", userName=" + getUserName() + ", accountName=" + getAccount().getAccountName() + "]";
    }

}