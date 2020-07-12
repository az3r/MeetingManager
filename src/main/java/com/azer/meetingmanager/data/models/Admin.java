package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    
}