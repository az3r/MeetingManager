package com.azer.meetingmanager.data.repositories;

import java.util.Arrays;
import java.util.List;

import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.Utility;

import org.hibernate.Session;

public class UserRepository extends Repository<User> {

	private static final String TAG = "UserRepository";

	public UserRepository(Session session) {
		super(session);
	}

	public User find(int id) {
		return session.find(User.class, id);
	}

	public void insertUser(List<User> users) {
		for (User user : users) {
			session.persist(user);
		}
	}

	public boolean ifExist(int userId) {
		return session.find(User.class, userId) != null;
	}

	public void insertAdmin(List<Admin> admins) {
		for (Admin admin : admins) {
			session.persist(admin);
		}
	}

	public void insertAdmin(Admin entity) {
		session.persist(entity);
	}

	public void addToPending(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		user.getPendingMeetings().add(meeting);
	}

	public void addToAccepted(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		user.getAcceptedMeetings().add(meeting);
	}

	public void removeFromPending(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		user.getPendingMeetings().remove(meeting);
	}

	public void removeFromAccepted(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		user.getAcceptedMeetings().remove(meeting);
	}

	public boolean isAccepted(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		return user != null ? user.getAcceptedMeetings().contains(meeting) : false;
	}

	public boolean isPending(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		return user != null ? user.getPendingMeetings().contains(meeting) : false;
	}

	public User ifExistSelect(String accountName, String password) {
		User user = session.createQuery("from User where accountName = :accountName", User.class)
				.setParameter("accountName", accountName).uniqueResult();
		if (user == null)
			return null;

		byte[] salt = user.getAccount().getSalt();
		byte[] hashedPassword = Utility.generatePassword(password, salt);

		return Arrays.equals(hashedPassword, user.getAccount().getPassword()) ? user : null;
	}

	public List<Meeting> getAcceptedMeeting(int userId) {
		return session.createQuery("select acceptedMeetings from User where userId = :userId")
				.setParameter("userId", userId).list();
	}

	public List<User> get() {
		return session.createQuery("from User where userType = :userType").setParameter("userType", "member").list();
	}

}