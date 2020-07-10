package com.azer.meetingmanager.ui.items;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.detail.MeetingDetailView;
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
            MeetingDetailView view = new MeetingDetailView(data);
            timeLabel.getScene().setRoot(view.getRoot());
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
