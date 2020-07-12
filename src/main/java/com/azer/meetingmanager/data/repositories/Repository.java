package com.azer.meetingmanager.data.repositories;

import org.hibernate.Session;

public abstract class Repository<T> implements IRepository<T> {

    protected Session session;

    public Repository(Session session) {
        if (session == null) {
            throw new NullPointerException("session is null");
        }
        this.session = session;
    }

    public void close() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public void delete(T entity) {
        session.remove(entity);

    }

    @Override
    public void insert(T entity) {
        session.persist(entity);

    }

    @Override
    public void update(T entity) {
        session.merge(entity);
    }

    @Override
    public void flush() {
        session.flush();
    }
}