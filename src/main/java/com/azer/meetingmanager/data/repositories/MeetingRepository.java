package com.azer.meetingmanager.data.repositories;

import java.util.List;
import java.util.Set;

import javax.persistence.RollbackException;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;

import org.hibernate.Session;
import org.hibernate.Transaction;

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

    public Set<User> getAcceptedUsers(Meeting entity) {
        Meeting meeting = session.find(Meeting.class, entity.getMeetingId());
        return meeting.getAcceptedUsers();
    }

    public Set<User> getPendingUsers(Meeting entity) {
        Meeting meeting = session.find(Meeting.class, entity.getMeetingId());
        return meeting.getPendingUsers();
    }

	public List<Meeting> get() {
		return session.createQuery("from Meeting order by holdTime DESC", Meeting.class).list();
	}
}