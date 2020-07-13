package com.azer.meetingmanager.data.repositories;

import javax.persistence.RollbackException;

import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class Repository<T> implements IRepository<T> {

    protected Session session;
    protected Transaction transaction;

    public Repository(Session session) {
        if (session == null) {
            throw new NullPointerException("session is null");
        }
        this.session = session;
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
    public void commit() throws RollbackException {
        session.getTransaction().commit();
    }

    @Override
    public void rollback() {
        session.getTransaction().rollback();
    }

    @Override
    public void close() {
        if (session != null) {
            session.close();
        }
    }
}