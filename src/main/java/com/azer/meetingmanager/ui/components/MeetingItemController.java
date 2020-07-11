package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.detail.MeetingDetailController;

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

    private Meeting data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupPhoPane();
        setupOverlay();
    }

    private void setupPhoPane() {
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    private void setupOverlay() {
        overlayController.setLeftButtonText("Detail");
        overlayController.setRightButtonText("Register");
        overlayController.setLeftButtonOnAction(e -> {
            ViewLoader<MeetingDetailController> loader = new ViewLoader<>("views/MeetingDetail.fxml", timeLabel.getScene().getRoot());
            loader.getController().setPreviousParent(loader.getPreviousParent());
            timeLabel.getScene().setRoot(loader.getRoot());
        });
        overlayController.setRightButtonOnAction(e -> {

        });
    }

    public void notifyDataChanged(Meeting data) {
        this.data = data;
        inflate();
    }

    private void inflate() {
        boolean hasPhoto = data.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
    }

}
