package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "userType", length = 50)
@DiscriminatorValue("member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(length = 50)
    private String userName;

    @Column(length = 50)
    private String userEmail;

    @Embedded
    private Account account;

    public User() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", accountName=" + account.getAccountName() + "]";
    }

    public User(String userName, String userEmail, Account account) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.account = account;
    }
}