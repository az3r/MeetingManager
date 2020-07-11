package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.OnItemActionListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Represent a meeting item, provide 2 options to interact with
 */
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
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    /**
     * notify that compoent should update its visual to new data
     * @param data the new data 
     */
    public void notifyDataChanged(Meeting data) {
        this.data = data;
        inflate();
    }

    public void setLeftButtonText(String text) {
        overlayController.setLeftButtonText(text);
    }

    public void setRightButtonText(String text) {
        overlayController.setRightButtonText(text);
    }

    public void setLeftButtonAction(OnItemActionListener<Meeting> listener, Meeting value) {
        overlayController.setLeftButtonAction(e -> listener.onAction(value));
    }

    public void setRightButtonAction(OnItemActionListener<Meeting> listener, Meeting value) {
        overlayController.setRightButtonAction(e -> listener.onAction(value));
    }


    private void inflate() {
        boolean hasPhoto = data.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
    }

}
