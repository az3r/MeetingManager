package com.azer.meetingmanager.ui.master;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.containers.MeetingContainerController;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MasterController implements Initializable {

    private static final String DISPLAY_LIST = "List view";
    private static final String DISPLAY_CARD = "Card view";

    @FXML
    private TopbarController topbarController;

    @FXML
    private MeetingContainerController meetingContainerController;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        changeMeetingContainer(true);
    }

    private void setupTopbar() {
        topbarController.setTitle("Meetings");
        topbarController.showBackButton(false);
    }

    private void changeMeetingContainer(boolean vbox) {
        displayButton.setText(vbox ? DISPLAY_CARD : DISPLAY_LIST);
        meetingContainerController.changeContainerPane(vbox);
    }

    @FXML
    public void onChangeDisplay(ActionEvent event) {
        changeMeetingContainer(displayButton.getText().equals(DISPLAY_LIST));
    }
}