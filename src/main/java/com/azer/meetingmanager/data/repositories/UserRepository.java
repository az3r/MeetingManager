package com.azer.meetingmanager.data.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserRepository extends Repository<User> {

	public UserRepository(Session session) {
		super(session);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void insert(User entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(User entity) {
		// TODO Auto-generated method stub

	}

	public List<User> getMember() {
		if (session == null) {
			users = getAll();
		}
		// List<User> customers = users.stream().filter(user -> !user.getIsAdmin()).collect(Collectors.toList());
		return null;
	}
	public List<Admin> getAdmin() {
		Session session = sessionFactory.openSession();
		Query<User> query = session.createQuery("from User", User.class);
		users = query.list();
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
}