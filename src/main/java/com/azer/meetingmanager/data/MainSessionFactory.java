package com.azer.meetingmanager.data;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MainSessionFactory {

    private SessionFactory sessionFactory;

    public MainSessionFactory() {

        initialize();
    }

    public MainSessionFactory(String resourceFile) {

        initialize(resourceFile);
    }

    private void initialize() throws ExceptionInInitializerError {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private void initialize(String resourceFile) throws ExceptionInInitializerError {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(resourceFile)
                    .build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void close() throws HibernateException {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}