package com.azer.meetingmanager.data.repositories;

import java.util.List;

import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.AccountHelper;

import org.hibernate.Session;
import org.hibernate.Transaction;

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

	public boolean addToPending(User entity, Meeting meeting) {
		User user = session.find(User.class, entity.getUserId());
		user.getPendingMeetings().add(meeting);
		return true;
	}

	public void removeFromPending(User entity, Meeting meeting) {
		Transaction tx = session.beginTransaction();
		User user = session.find(User.class, entity.getUserId());
		user.getPendingMeetings().remove(meeting);
		tx.commit();
	}

	public void removeFromAccepted(User entity, Meeting meeting) {
		Transaction tx = session.beginTransaction();
		User user = session.find(User.class, entity.getUserId());
		user.getAcceptedMeetings().remove(meeting);
		tx.commit();
	}

	public boolean isRequestAccepted(User entity, Meeting meeting) {
		session.beginTransaction();
		User user = session.find(User.class, entity.getUserId());
		boolean registered = user.getAcceptedMeetings().contains(meeting);
		session.getTransaction().commit();
		return registered;
	}

	public boolean isRequestPending(User entity, Meeting meeting) {
		session.beginTransaction();
		User user = session.find(User.class, entity.getUserId());
		System.out.println(user.getPendingMeetings().size());
		boolean registered = user.getPendingMeetings().contains(meeting);
		session.getTransaction().commit();
		return registered;
	}

	public boolean ifExist(String accountName, String password) {
		User user = session.createQuery("from User where accountName = :accountName", User.class).setParameter("accountName", accountName)
		.uniqueResult();
		byte[] salt = user.getAccount().getSalt();
		byte[] hashedPassword = AccountHelper.generatePassword(password, salt);
		return hashedPassword == user.getAccount().getPassword();
	}

}