package com.azer.meetingmanager.data.repositories;

public interface IRepository<T> {
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
    void flush();
}

