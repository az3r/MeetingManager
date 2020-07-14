package com.azer.meetingmanager.data.repositories;

public interface IRepository<T> {
    void insert(T entity);

    void update(T entity);

    void delete(T entity);

    /**
     * call this function before closing repository and after you have finished user
     * request
     */
    void commit();

    void rollback();

    void close();
}
