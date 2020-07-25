package com.azer.meetingmanager.data.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    @Column
    private boolean blocked;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "PendingRequest",
        joinColumns = { @JoinColumn(referencedColumnName = "userId") },
        inverseJoinColumns = { @JoinColumn(columnDefinition = "meetingId") }
    )
    private Set<Meeting> pendingMeetings = new HashSet<>();

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "AcceptedRequest",
        joinColumns = { @JoinColumn(referencedColumnName = "userId") },
        inverseJoinColumns = { @JoinColumn(columnDefinition = "meetingId") }
    )
    private Set<Meeting> acceptedMeetings = new HashSet<>();

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

    public User(String userName, String userEmail, boolean blocked, Account account) {
        this.userName = userName;
        this.blocked = blocked;
        this.userEmail = userEmail;
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        User user = (User) obj;
        return this.getUserId() == user.getUserId() && this.getAccount().equals(user.getAccount());
    }

    @Override
    public int hashCode() {
        return this.getUserId();
    }

    public Set<Meeting> getPendingMeetings() {
        return pendingMeetings;
    }

    public void setPendingMeetings(Set<Meeting> pendingMeetings) {
        this.pendingMeetings = pendingMeetings;
    }

    public Set<Meeting> getAcceptedMeetings() {
        return acceptedMeetings;
    }

    public void setAcceptedMeetings(Set<Meeting> acceptedMeetings) {
        this.acceptedMeetings = acceptedMeetings;
    }

    public boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "User [blocked=" + blocked + ", userEmail=" + userEmail + ", userId=" + userId + ", userName=" + userName
                + "]";
    }
}