package com.azer.meetingmanager.data.models;

import java.util.Date;

import javax.persistence.*;

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

    @Column(length = 100000)
    private byte[] photo;

    @Column
    private Date holdTime;

    @Column
    private int duration;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "locationId", referencedColumnName = "locationId")
    private Location location;

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

    public Meeting(String name, String shortDesc, String detailDesc, byte[] photo, Date holdTime, int duration, Location location) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.detailDesc = detailDesc;
        this.photo = photo;
        this.holdTime = holdTime;
        this.duration = duration;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Meeting [duration=" + duration + ", holdTime=" + holdTime + ", " + location + ", meetingId=" + meetingId
                + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}