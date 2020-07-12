package com.azer.meetingmanager.data.login;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;

public class LoggedUserResource {

    private static LoggedUserResource instance = null;

    private User loggedUser;
    private UserRepository repository = new UserRepository(App.getSessionFactory().openSession());

    public boolean isMember() {
        return !(loggedUser instanceof Admin);
    }

    public boolean isAdmin() {
        return !isMember();
    }

    public boolean isGuess() {
        return loggedUser == null;
    }

    public User getUser() {
        return loggedUser;
    }

    public Admin getAdmin() {
        return (Admin) loggedUser;
    }

    public static LoggedUserResource getInstance() {
        if (instance == null) {
            synchronized (LoggedUserResource.class) {
                if (instance == null) {
                    instance = new LoggedUserResource();
                }
            }
        }
        return instance;
    }

    public boolean login(String accountName, String password) {
        User user = repository.findAdmin(accountName, password);
        if (user == null) {
            user = repository.findUser(accountName, password);
        }
        loggedUser = user;
        return loggedUser != null;
    }
}