package com.azer.meetingmanager.data.models;

public class Location {
    private int locationId;
    private String name;
    private String address;
    private int capactity;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCapactity() {
        return capactity;
    }

    public void setCapactity(int capactity) {
        this.capactity = capactity;
    }
}