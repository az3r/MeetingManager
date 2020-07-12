package com.azer.meetingmanager.data.repositories;

import com.azer.meetingmanager.data.models.Meeting;

import org.hibernate.Session;

public class MeetingRepository extends Repository<Meeting> {

    public MeetingRepository(Session session) {
        super(session);
    }
}