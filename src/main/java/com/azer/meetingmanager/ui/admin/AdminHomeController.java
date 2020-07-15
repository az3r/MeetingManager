package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AdminHomeController implements Initializable {

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
        meetingContainerController.setLeftButtonText("Edit");
        meetingContainerController.setLeftButtonListener(value -> {

        });

        meetingContainerController.setRightButtonText("Accept all");
        meetingContainerController.setRightButtonListener(value -> {

        });

    }

    private void setupTopbar() {
        topbarController.setTitle("Manage meetings");
        topbarController.showAddButton(true);
        topbarController.setAddButtonAction(e -> {

        });
    }
}