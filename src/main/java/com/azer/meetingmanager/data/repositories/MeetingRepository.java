package com.azer.meetingmanager.data.repositories;

import java.util.List;
import java.util.Set;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;

import org.hibernate.Session;

public class MeetingRepository extends Repository<Meeting> {

    public MeetingRepository(Session session) {
        super(session);
    }

    public void insert(List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            session.persist(meeting);
        }
    }

    public Meeting getLatestMeeting() {
        return session.createQuery("from Meeting where ended = false order by holdTime DESC", Meeting.class)
                .setMaxResults(1).uniqueResult();
    }

    public List<User> getAcceptedUsers(int meetingId) {
        return session.createQuery("select acceptedUsers from Meeting where meetingId = :meetingId order by userName")
        .setParameter("meetingId", meetingId)
        .list();
    }

    public List<User> getPendingUsers(int meetingId) {
        return session.createQuery("select pendingUsers from Meeting where meetingId = :meetingId order by userName")
        .setParameter("meetingId", meetingId)
        .list();
    }

    public List<Meeting> get(boolean includeEnded) {
        String query = includeEnded? "from Meeting order by ended ASC, holdTime DESC, name ASC" : "from Meeting where ended = false order by holdTime DESC, name ASC";
        return session.createQuery(query, Meeting.class).list();
    }

    public int countPendingUsers(int meetingId) {
        return session.find(Meeting.class, meetingId).getPendingUsers().size();
    }

    public int countAcceptedUsers(int meetingId) {
        return session.find(Meeting.class, meetingId).getAcceptedUsers().size();
    }

    public Meeting find(int meetingId) {
        return session.find(Meeting.class, meetingId);
    }

}