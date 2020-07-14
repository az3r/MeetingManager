package com.azer.meetingmanager.data;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Account;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.AccountHelper;

public class LoggedUserResource {

    private static LoggedUserResource instance = null;

    private User loggedUser;

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
        User user = App.getUnitOfWork().login(accountName, password);
        loggedUser = user;
        return loggedUser != null;
    }

    public boolean register(String userName, String userEmail, String accountName, String password) {
        byte[] salt = AccountHelper.generateSalt(16);
        byte[] hashedPassword = AccountHelper.generatePassword(password, salt);
        Account account = new Account(accountName, salt, hashedPassword);
        User user = new User(userName, userEmail, account);
        boolean result = App.getUnitOfWork().registerUser(user);
        if (result) this.loggedUser = user;
        return result;
    }

    public void logout() {
        loggedUser = null;
    }
}