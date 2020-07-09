package com.azer.meetingmanager.ui.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MeetingItemController implements Initializable {

    @FXML
    private ImageView photoImageView;

    @FXML
    private Label timeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label pendingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void notifyDataChanged(Meeting data) {
        
    }

}
