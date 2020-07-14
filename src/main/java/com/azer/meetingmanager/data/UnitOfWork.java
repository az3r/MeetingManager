package com.azer.meetingmanager.data;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
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
        Log.i(TAG, String.format("finding user with accountName=%s, password=%s", accountName, password));
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        User user = repository.ifExist(accountName, password) ? repository.find(accountName) : null;
        if (user != null)
            Log.i(TAG, "found " + user);
        else
            Log.i(TAG, "no user found");
        repository.close();
        return user;
    }

    public Meeting getLatestMeeting() {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        Meeting meeting = repository.getLatestMeeting();
        Log.i(TAG, "latest " + meeting);
        repository.close();
        return meeting;
    }

    public boolean isAccepted(User user, Meeting meeting) {
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        boolean accepted = repository.isAccepted(user, meeting);
        repository.close();
        return accepted;
    }

    public boolean isPending(User user, Meeting meeting) {
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        boolean accepted = repository.isAccepted(user, meeting);
        repository.close();
        return accepted;
    }
}