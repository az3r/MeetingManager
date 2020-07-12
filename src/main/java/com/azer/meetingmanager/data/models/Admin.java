package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    @Override
    public String toString() {
        return "Admin [userId= " + getUserId() + ", userName= " + getUserName() + "]";
    }
    
}