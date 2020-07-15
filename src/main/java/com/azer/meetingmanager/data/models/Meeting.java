package com.azer.meetingmanager.data.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String shortDesc;

    @Column(length = 1000)
    private String detailDesc;

    @Column(length = 100000, nullable = true)
    private byte[] photo;

    @Column
    private Date holdTime;

    @Column
    private boolean ended;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "locationId")
    private Location location;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<User> pendingUsers = new HashSet<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Fetch(FetchMode.SELECT)
    private Set<User> acceptedUsers = new HashSet<>();;

    public Meeting() {

    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getDetailDesc() {
        return detailDesc;
    }

    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }

    public Date getHoldTime() {
        return holdTime;
    }

    public void setHoldTime(Date holdTime) {
        this.holdTime = holdTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Meeting(String name, String shortDesc, String detailDesc, byte[] photo, Date holdTime, boolean ended,
            Location location) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.detailDesc = detailDesc;
        this.photo = photo;
        this.holdTime = holdTime;
        this.ended = ended;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Meeting)
            return ((Meeting) obj).getMeetingId() == this.getMeetingId();
        return false;
    }

    @Override
    public int hashCode() {
        return this.getMeetingId();
    }

    public Set<User> getPendingUsers() {
        return pendingUsers;
    }

    public void setPendingUsers(Set<User> pendingUsers) {
        this.pendingUsers = pendingUsers;
    }

    public Set<User> getAcceptedUsers() {
        return acceptedUsers;
    }

    public void setAcceptedUsers(Set<User> acceptedUsers) {
        this.acceptedUsers = acceptedUsers;
    }

    public boolean getEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    @Override
    public String toString() {
        return "Meeting [ended=" + ended + ", location=" + location + ", meetingId=" + meetingId + ", name=" + name
                + "]";
    }
}