package com.azer.meetingmanager.data.repositories;

import org.hibernate.SessionFactory;

public abstract class Repository<T> implements IRepository<T> {

    protected SessionFactory sessionFactory;

    public Repository(SessionFactory sessionFactory) {
        if (sessionFactory == null) {
            throw new NullPointerException("sessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
    }

}