package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

public class AdminHomeController implements Initializable {

    @FXML
    private Parent root;

    @FXML
    private TopbarController topbarController;

    @FXML
    private MeetingContainerController meetingContainerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupMeetingItem();
    }

    private void setupMeetingItem() {
        meetingContainerController.setLeftButtonText("Detail");
        meetingContainerController.setLeftButtonListener(value -> {

        });

        meetingContainerController.setRightButtonText("Pending");
        meetingContainerController.setRightButtonListener(value -> {

        });

        List<Meeting> allMeetings = App.getUnitOfWork().getAllMeetings();
        meetingContainerController.notifyCollectionChanged(allMeetings);

    }

    private void setupTopbar() {
        topbarController.setTitle("Manage meetings");
        topbarController.showAddButton(true);
        topbarController.setAddButtonAction(e -> {
            ViewLoader<MeetingEditorController> loader = new ViewLoader<>("views/MeetingEditor.fxml");
            loader.getController().setUpParent(root.getScene().getRoot());
            root.getScene().setRoot(loader.getRoot());
        });
    }

}