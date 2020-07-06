package com.azer.meetingmanager.data.models;

import java.util.Date;

public class Meeting {
    private int meetingId;
    private String shortDesc;
    private String detailDesc;
    private String photo;
    private Date hold_time;
    private int locationId;


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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Date getHold_time() {
        return hold_time;
    }

    public void setHold_time(Date hold_time) {
        this.hold_time = hold_time;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}