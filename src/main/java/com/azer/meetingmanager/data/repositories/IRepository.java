package com.azer.meetingmanager.data.repositories;

import java.util.List;

public interface IRepository<T> {
    void insert(T entity);
    void update(T entity);
    List<T> getAll();
}

