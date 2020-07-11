package com.azer.meetingmanager.ui.detail;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MeetingDetailController implements Initializable {

    @FXML
    private TopbarController topbarController;

    @FXML
    private ImageView photoImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label pendingLabel;

    @FXML
    private Label detailLabel;

    @FXML
    private StackPane photoEmptyPane;

    private Parent previousNode;

    public void notifyDataChanged(Meeting data) {
        inflate(data);
    }

    private void inflate(Meeting data) {
        boolean hasPhoto = data.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupPhotoPane();
    }

    private void setupPhotoPane() {
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    private void setupTopbar() {
        topbarController.showSearchOption(false);
        topbarController.setOnBackAction(e -> {
            timeLabel.getScene().setRoot(previousNode);
        });
    }

    /**
     * set the previous view that performs navigating to this view
     */
    public void setPreviousNode(Parent previousNode) {
        this.previousNode = previousNode;
    }
}