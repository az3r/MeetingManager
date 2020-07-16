package com.azer.meetingmanager.data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.helpers.RequestResult;

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
        User user = repository.ifExistSelect(accountName, password);
        if (user != null)
            Log.i(TAG, "found " + user);
        else
            Log.i(TAG, "no user found");
        repository.close();
        return user;
    }

    public boolean updateUser(User user) {
        Log.i(TAG, "updating " + user.toString());

        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        repository.update(user);

        boolean result = false;
        try {
            repository.commit();
            result = true;
            Log.i(TAG, "update user successfully");

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            repository.close();
        }
        return result;
    }

    public Meeting getLatestMeeting() {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        Meeting meeting = repository.getLatestMeeting();
        Log.i(TAG, "latest " + meeting);
        repository.close();
        return meeting;
    }

    public RequestResult registerMeeting(User user, Meeting meeting) {
        Log.i(TAG, String.format("register %s to pending list of %s...", user, meeting));
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());

        RequestResult result = null;
        if (repository.isAccepted(user.getUserId(), meeting))
            result = RequestResult.ACCEPTED;
        if (repository.isPending(user.getUserId(), meeting))
            result = RequestResult.ALREADY_PENDING;
        else {
            try {
                repository.addToPending(user.getUserId(), meeting);
                repository.commit();
                result = RequestResult.PENDING;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
                repository.rollback();
            }
        }

        switch (result) {
            case ACCEPTED:
                Log.i(TAG, "request have already been accepted");
                break;
            case ALREADY_PENDING:
                Log.i(TAG, "request have already been in pending list");
                break;
            case PENDING:
                Log.i(TAG, "request have been added to pending list");
                break;
            default:
                Log.e(TAG, "unable to register user to meeting");
                break;
        }

        repository.close();
        return result;
    }

    public boolean cancelMeeting(User user, Meeting meeting) {
        Log.i(TAG, String.format("canceling request of %s to %s...", user, meeting));
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());

        repository.removeFromAccepted(user.getUserId(), meeting);
        repository.removeFromPending(user.getUserId(), meeting);

        boolean result = false;
        try {
            repository.commit();
            result = true;
            Log.i(TAG, "remove successfully");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            repository.rollback();
        } finally {
            repository.close();
        }
        return result;
    }

    public List<Meeting> getAllMeetings() {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        List<Meeting> collection = repository.get(true);
        repository.close();
        return collection;
    }

    public List<Meeting> getOpeningMeetings() {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        List<Meeting> collection = repository.get(false);
        repository.close();
        return collection;
    }

    public int countPendingUsers(int meetingId) {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        int size = repository.countPendingUsers(meetingId);
        repository.close();
        return size;
    }

    public int countAcceptedUsers(int meetingId) {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        int size = repository.countPendingUsers(meetingId);
        repository.close();
        return size;
    }

    public Collection<Meeting> getAcceptedMeetings(User user) {
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        Collection<Meeting> meetings = repository.getAcceptedMeeting(user.getUserId());
        repository.close();
        return meetings;
    }

    public boolean createMeeting(Meeting meeting) {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        repository.insert(meeting);
        boolean result = false;
        try {
            repository.commit();
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        repository.close();
        return result;
    }

    public boolean updateMeeting(Meeting meeting) {
        Log.i(TAG, "update " + meeting.toString());
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        repository.update(meeting);
        boolean result = false;
        try {
            repository.commit();
            result = true;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        repository.close();
        return result;
    }

    public boolean acceptRequest(User user, Meeting meeting) {
        Log.i(TAG, String.format("accept request of %s to %s", user, meeting));
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());

        boolean result = false;
        meeting = repository.find(meeting.getMeetingId());
        Set<User> acceptedUsers = meeting.getAcceptedUsers();
        Set<User> pendingUsers = meeting.getPendingUsers();
        Log.i(TAG, String.valueOf(acceptedUsers.size()));
        Log.i(TAG, String.valueOf(pendingUsers.size()));
        if (pendingUsers.contains(user) && acceptedUsers.size() < meeting.getLocation().getCapacity()) {
            try {
                pendingUsers.remove(user);
                acceptedUsers.add(user);
                repository.commit();

                Log.i(TAG, "accept successfully");
                Log.i(TAG, String.valueOf(acceptedUsers.size()));
                Log.i(TAG, String.valueOf(pendingUsers.size()));
                result = true;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Log.i(TAG, "can not accept request because meeting is full");
        }

        repository.close();
        return result;
    }

    public boolean denyRequest(User user, Meeting meeting) {
        Log.i(TAG, String.format("deny request of %s to %s", user, meeting));
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());

        boolean result = false;
        meeting = repository.find(meeting.getMeetingId());
        Set<User> pendingUsers = meeting.getPendingUsers();
        if (pendingUsers.contains(user)) {
            try {
                pendingUsers.remove(user);
                repository.commit();
                Log.i(TAG, "deny successfully");
                result = true;
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }

        repository.close();
        return result;
    }

    public boolean blockUser(User user) {
        Log.i(TAG, "block " + user.toString());
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        user.setBlocked(true);
        repository.update(user);
        boolean success = false;
        try {
            repository.commit();
            success = true;
            Log.i(TAG, "block successfully");
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        repository.close();
        return success;
    }

    public boolean unblockUser(User user) {
        Log.i(TAG, "unblock " + user.toString());
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        user.setBlocked(false);
        repository.update(user);
        boolean success = false;
        try {
            repository.commit();
            success = true;
            Log.i(TAG, "unblock successfully");
        } catch (Exception e) {
            success = false;
            Log.e(TAG, e.toString());
        }
        repository.close();
        return success;
    }

    public List<User> getUsers() {
        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        List<User> result = repository.get();
        repository.close();
        return result;
    }

    public List<User> getPendingUser(Meeting meeting) {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        List<User> users = repository.getPendingUsers(meeting.getMeetingId());
        repository.close();
        return users;
    }
}