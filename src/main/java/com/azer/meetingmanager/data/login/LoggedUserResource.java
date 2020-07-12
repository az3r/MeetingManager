package com.azer.meetingmanager.data.login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Account;
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

    public boolean register(String userName, String userEmail, String accountName, String password) {
        System.out.println("preparing to register new account: " + accountName);
        System.out.println("checking if an account with same name exists in database...");
        User user = repository.findUser(accountName);
        if (user == null) {
            System.out.println("No duplicate found! Registering new account...");

            System.out.println("generating salt...");
            byte[] salt = generateSalt(16);

            System.out.println("hashing password...");
            byte[] hashedPassword = generatePassword(password, salt);

            Account account = new Account(accountName, salt, hashedPassword);
            user = new User(userName, userEmail, account);
            System.out.println("registering " + user.toString());
            if (repository.insert(user)) {
                loggedUser = user;
                System.out.println("register successfully!");
                return true;
            }
            return false;

        }
        return false;
    }

    public void logout() {
        loggedUser = null;
    }

    private static byte[] generateSalt(int size) {
        SecureRandom secureGenerator = new SecureRandom();
        byte[] salt = new byte[16];
        secureGenerator.nextBytes(salt);
        return salt;
    }

    private static byte[] generatePassword(String password, byte[] salt) {
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = null;
        if (digester != null) {
            digester.update(salt);
            hashedPassword = digester.digest(password.getBytes());
        }
        return hashedPassword;
    }
}