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
        return session.createQuery("from Meeting order by holdTime DESC", Meeting.class).setMaxResults(1)
                .uniqueResult();
    }

    public Set<User> getAcceptedUsers(int meetingId) {
        Meeting meeting = session.find(Meeting.class, meetingId);
        return meeting.getAcceptedUsers();
    }

    public Set<User> getPendingUsers(int meetingId) {
        Meeting meeting = session.find(Meeting.class, meetingId);
        return meeting.getPendingUsers();
    }

    public List<Meeting> get() {
        return session.createQuery("from Meeting order by holdTime DESC", Meeting.class).list();
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