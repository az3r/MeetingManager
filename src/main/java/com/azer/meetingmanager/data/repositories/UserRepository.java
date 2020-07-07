package com.azer.meetingmanager.data.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.azer.meetingmanager.data.models.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UserRepository extends Repository<User> {

	private List<User> users = null;

	public UserRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
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

	@Override
	public List<User> getAll() {
		if (users == null) {
			Session session = sessionFactory.openSession();
			Query<User> query = session.createQuery("select * from user", User.class);
			users = query.list();
		}

		return users;
	}

	public List<User> getCustomers() {
		if (users == null) {
			users = getAll();
		}
		List<User> customers = users.stream().filter(user -> !user.getIsAdmin()).collect(Collectors.toList());
		return customers;
	}
}