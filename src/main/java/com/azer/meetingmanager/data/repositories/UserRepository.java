package com.azer.meetingmanager.data.repositories;

import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;

import org.hibernate.Session;

public class UserRepository extends Repository<User> {

	private static final String TAG = "UserRepository";

	public UserRepository(Session session) {
		super(session);
	}

	public User find(int id) {
		return session.find(User.class, id);
	}

	public Admin findAdmin(String accountName, String password) {
		System.out.println(String.format("%s: finding admin with name=%s, password=%s", TAG, accountName, password));
		Admin result = session.createQuery("from Admin where accountName = :accountName", Admin.class)
				.setParameter("accountName", accountName).uniqueResult();

		if (result == null) System.out.println("No admin was found");
		else System.out.println("admin found: " + result.toString());

		return result;
	}
}