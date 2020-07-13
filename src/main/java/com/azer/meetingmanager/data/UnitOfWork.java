package com.azer.meetingmanager.data;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;

public class UnitOfWork {
    private static final String TAG = "UnitOfWork";

    public boolean registerUser(User user) {
        Log.i(TAG, String.format("registering %s...", user));

        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        if (repository.ifExist(user.getUserId())) {
            Log.i(TAG, "failed to register: account already existed");
            return false;
        }
        repository.insert(user);

        boolean success = false;
        try {
            repository.commit();
            success = true;
            Log.i(TAG, "register successfully");
        } catch (Exception e) {
            Log.e(TAG, "register failed...");
            Log.e(TAG, e.toString());
        }
        repository.close();
        return success;
    }

    public User login(String accountName, String password) {
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        if (repository.ifExist(accountName, password))
            return repository.find(accountName);
        return null;
    }

}