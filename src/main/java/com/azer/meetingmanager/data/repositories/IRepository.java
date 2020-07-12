package com.azer.meetingmanager.data.repositories;

public interface IRepository<T> {
    boolean insert(T entity);
    void update(T entity);
    void delete(T entity);
}

