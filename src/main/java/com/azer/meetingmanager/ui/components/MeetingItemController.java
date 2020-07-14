package com.azer.meetingmanager.ui.components;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.ui.OnItemActionListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Represent a meeting item, provide 2 options to interact with
 */
public class MeetingItemController implements Initializable {

    @FXML
    private StackPane photoEmptyPane;

    @FXML
    private ImageView photoImageView;

    @FXML
    private Label timeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label capacityLabel;

    @FXML
    private Label pendingLabel;

    @FXML
    private OverlayController overlayController;

    private Meeting meeting;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    /**
     * notify that compoent should update its visual to reponse to data changed
     * 
     * @param meeting the new data
     */
    public void notifyDataChanged(Meeting meeting) {
        this.meeting = meeting;
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

        int acceptedCount = App.getUnitOfWork().countAcceptedUsers(meeting.getMeetingId());
        int pendingCount = App.getUnitOfWork().countPendingUsers(meeting.getMeetingId());

        titleLabel.setText(meeting.getName());

        String capacity = String.format("%d / %d", acceptedCount, meeting.getLocation().getCapacity());
        capacityLabel.setText(capacity);

        String date = DateFormat.getDateTimeInstance().format(meeting.getHoldTime());
        timeLabel.setText(date);

        String location = String.format("%s - %s", meeting.getLocation().getName(), meeting.getLocation().getAddress());
        locationLabel.setText(location);

        String pending = String.valueOf(pendingCount);
        pendingLabel.setText(pending);

        boolean hasPhoto = meeting.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);
        
        if (hasPhoto)
            photoImageView.setImage(new Image(new ByteArrayInputStream(meeting.getPhoto())));
    }

}
