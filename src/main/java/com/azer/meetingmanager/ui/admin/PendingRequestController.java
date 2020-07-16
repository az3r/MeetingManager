package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.components.UserItemContainerController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class PendingRequestController extends BackableController implements Initializable {

    private static final String TAG = "PendingRequestController";

    @FXML
    private UserItemContainerController userItemContainerController;

    @FXML
    private TopbarController topbarController;

    @FXML
    private Parent root;

    @FXML
    private Label currentLabel;

    @FXML
    private Label capacityLabel;

    private Meeting meeting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            root.getScene().setRoot(getUpParent());
        });

        userItemContainerController.setOnAcceptListener(user -> {
            int value = Integer.parseInt(currentLabel.getText());
            currentLabel.setText(String.valueOf(value + 1));
        });
    }

    private void inflateContainer() {
        if (meeting != null) {
            Log.i(TAG, "load pending request from " + meeting.toString());
            List<User> pendingUsers = App.getUnitOfWork().getPendingUser(meeting);
            userItemContainerController.notifyCollectionChanged(pendingUsers);
            userItemContainerController.setItemActionType(false);
            userItemContainerController.setMeeting(meeting);
        } else {
            Log.e(TAG, "can not get pending users because meeting is null");
        }
    }

    public void notifyMeetingChanged(Meeting meeting) {
        this.meeting = meeting;

        int capacity = meeting.getLocation().getCapacity();
        capacityLabel.setText(String.valueOf(capacity));

        int currentCount = App.getUnitOfWork().countPendingUsers(meeting.getMeetingId());
        currentLabel.setText(String.valueOf(currentCount));

        inflateContainer();
    }

}
