package com.azer.meetingmanager.ui.detail;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.components.UserItemContainerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AcceptedListController extends BackableController implements Initializable {
    @FXML
    private Parent root;

    @FXML
    private UserItemContainerController userItemContainerController;
    @FXML
    private TopbarController topbarController;

    private Meeting meeting;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topbarController.setTitle("Attendance users");
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> root.getScene().setRoot(getUpParent()));

        userItemContainerController.setItemActionType(UserItemContainerController.ACTION_NONE);
    }

    public void notifyDataChanged(Meeting meeting) {
        this.meeting = meeting;
        List<User> acceptedUsers = App.getUnitOfWork().getAcceptedUsers(meeting);
        userItemContainerController.notifyCollectionChanged(acceptedUsers);
    }
}
