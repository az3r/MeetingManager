package com.azer.meetingmanager.data.repositories;

import java.util.List;

import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.AccountHelper;

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

	public Admin findAdmin(String accountName, String password) {
		System.out.println(String.format("%s: finding admin with name=%s, password=%s", TAG, accountName, password));
		Admin result = session.createQuery("from Admin where accountName = :accountName", Admin.class)
				.setParameter("accountName", accountName).uniqueResult();

		if (result == null)
			System.out.println("No admin was found");
		else
			System.out.println("admin found: " + result.toString());

		return result;
	}

	public User findUser(String accountName, String password) {
		System.out.println(String.format("%s: finding admin with name=%s, password=%s", TAG, accountName, password));
		User result = session.createQuery("from User where accountName = :accountName", User.class)
				.setParameter("accountName", accountName).setMaxResults(1).uniqueResult();

		if (result == null)
			System.out.println("No admin was found");
		else
			System.out.println("admin found: " + result.toString());

		return result;
	}

	public User find(String accountName) {
		return session.createQuery("from User where accountName = :accountName", User.class)
				.setParameter("accountName", accountName).uniqueResult();
	}

	public void addToPending(int userId, Meeting meeting) {
		User user = session.find(User.class, userId);
		user.getPendingMeetings().add(meeting);
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

	public boolean ifExist(String accountName, String password) {
		User user = session.createQuery("from User where accountName = :accountName", User.class)
				.setParameter("accountName", accountName).uniqueResult();
		byte[] salt = user.getAccount().getSalt();
		byte[] hashedPassword = AccountHelper.generatePassword(password, salt);
		return hashedPassword == user.getAccount().getPassword();
	}

}