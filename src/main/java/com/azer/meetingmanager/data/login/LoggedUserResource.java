package com.azer.meetingmanager.data.login;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;

public class LoggedUserResource {
    private User loggedUser;
    private UserRepository repository = new UserRepository(App.getSessionFactory());

    public boolean isMember() {
        return !(loggedUser instanceof Admin);
    }
    public boolean isAdmin() {
        return !isMember();
    }
    public User getUser() {
        return loggedUser;
    }
    public Admin getAdmin() {
        return (Admin) loggedUser;
    }
    public void login(String accountName, String password) {
        repository.get
    }
}