package com.azer.meetingmanager.data.repositories;

import java.util.List;

import com.azer.meetingmanager.data.models.Meeting;

import org.hibernate.SessionFactory;

public class MeetingRepository extends Repository<Meeting> {

    public MeetingRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void insert(Meeting entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Meeting entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Meeting> getAll() {
        return null;
        // TODO Auto-generated method stub

    }
    
}