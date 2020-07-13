package com.azer.meetingmanager.data.repositories;

import java.util.List;

import javax.persistence.RollbackException;

import com.azer.meetingmanager.data.models.Meeting;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class MeetingRepository extends Repository<Meeting> {

    public MeetingRepository(Session session) {
        super(session);
    }

    public boolean insert(List<Meeting> meetings) {
        Transaction transaction = session.beginTransaction();
        try {
            for (Meeting meeting : meetings) {
                session.persist(meeting);
            }
            transaction.commit();
            return true;
        } catch (RollbackException e) {
            System.err.println(e);
            transaction.rollback();
            return false;
        }
    }

    public Meeting getLatestMeeting() {
        System.out.println("retrieving latest meeting...");
        Meeting meeting = session.createQuery("from Meeting order by holdTime DESC", Meeting.class)
        .uniqueResult();
        if (meeting != null) System.out.println(meeting);
        else System.out.println("no meeting record in database!");
        return meeting;
    }
}