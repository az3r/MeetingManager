package com.azer.meetingmanager.ui.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.overlay.OverlayController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MeetingItemController implements Initializable {

    @FXML
    private OverlayController overlayController;

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

    @FXML
    private StackPane photoEmptyPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    public void notifyDataChanged(Meeting data) {
        inflate(data);
    }

    private void inflate(Meeting data) {
        boolean hasPhoto = data.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
    }

}
