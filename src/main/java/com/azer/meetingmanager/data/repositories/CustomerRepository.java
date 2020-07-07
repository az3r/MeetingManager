package com.azer.meetingmanager.data.repositories;

import com.azer.meetingmanager.data.models.Customer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CustomerRepository extends Repository<Customer> {

	public CustomerRepository(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public void insert(Customer entity) {

	}

	@Override
	public void update(Customer entity) {
		// TODO Auto-generated method stub
		
	}


    
}