package com.azer.meetingmanager.data.models;

import javax.persistence.*;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;

    @Column(length = 100)
    private String name;

    @Column(length = 100)
    private String address;

    @Column
    private int capacity;

    @OneToOne(mappedBy = "location")
    private Meeting meeting;

    public Location() {

    }

    public int getLocationId() {
        return locationId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public Location(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Location [address=" + address + ", capacity=" + capacity + ", name=" + name + "]";
    }

}