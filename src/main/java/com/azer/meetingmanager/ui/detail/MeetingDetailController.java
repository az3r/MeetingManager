package com.azer.meetingmanager.ui.detail;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    private Parent previousParent;

    public void notifyDataChanged(Meeting meeting) {

        titleLabel.setText(meeting.getName());
        detailLabel.setText(meeting.getDetailDesc());
        // capacityLabel.setText(meeting.getLocation().getCapacity());

        boolean hasPhoto = meeting.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
        if (hasPhoto)
            photoImageView.setImage(new Image(new ByteArrayInputStream(meeting.getPhoto())));
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
        topbarController.setTitle("Detail");
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            timeLabel.getScene().setRoot(previousParent);
        });
    }

    /**
     * set the Preivous root node that navigates to this view
     */
    public void setPreviousParent(Parent previousNode) {
        this.previousParent = previousNode;
    }

    public Parent getPreviousParent() {
        return this.previousParent;
    }
}