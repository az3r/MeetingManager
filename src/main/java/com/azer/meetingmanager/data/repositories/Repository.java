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

    public void close() {
        if (session != null) {
            session.close();
        }
    }

    @Override
    public void delete(T entity) {
        Transaction tx = session.beginTransaction();
        session.remove(entity);
        tx.commit();
    }

    @Override
    public boolean insert(T entity) {
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        try {
            tx.commit();
            return true;

        } catch (RollbackException e) {
            System.err.println(e);
            tx.rollback();
            return false;
        }
    }

    @Override
    public void update(T entity) {
        Transaction tx = session.beginTransaction();
        session.merge(entity);
        tx.commit();
    }
}