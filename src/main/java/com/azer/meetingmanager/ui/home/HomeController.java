package com.azer.meetingmanager.ui.home;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;

import org.hibernate.SessionFactory;

public class HomeController {

    private SessionFactory sessionFactory;
    private UserRepository userRepository;
    private MeetingRepository meetingRepository;

    public HomeController(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            throw new NullPointerException("sessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
        userRepository = new UserRepository(sessionFactory);
        meetingRepository = new MeetingRepository(sessionFactory);
    }
}