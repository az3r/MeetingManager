package com.azer.meetingmanager.ui.detail;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MeetingDetailController implements Initializable {

    @FXML
    private TopbarController topbarController;

    @FXML
    private Button registerButton;

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

    private Meeting meeting;
    private Parent previousParent;

    public void notifyDataChanged(Meeting meeting) {
        this.meeting = App.getUnitOfWork().fetchMeeting(meeting.getMeetingId());

        titleLabel.setText(meeting.getName());
        detailLabel.setText(meeting.getDetailDesc());

        String capacity = String.format("%d / %d", meeting.getAcceptedUsers().size(),
                meeting.getLocation().getCapacity());
        capacityLabel.setText(capacity);

        String date = DateFormat.getDateTimeInstance().format(meeting.getHoldTime());
        timeLabel.setText(date);

        String location = String.format("%s - %s", meeting.getLocation().getName(), meeting.getLocation().getAddress());
        locationLabel.setText(location);

        String pending = String.valueOf(meeting.getPendingUsers().size());
        pendingLabel.setText(pending);

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

    @FXML
    private void onRegister(ActionEvent e) {
        LoggedUserResource resource = LoggedUserResource.getInstance();

        if (resource.isGuess()) {
            Alert dialog = new Alert(AlertType.ERROR, "you must login first!", ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        User user = resource.getUser();

        String message = "";
        AlertType alertType = AlertType.INFORMATION;
        switch (App.getUnitOfWork().registerMeeting(user, meeting)) {
            case ACCEPTED:
                message = "you have already registered this meeting";
                break;
            case ALREADY_PENDING:
                message = "your request have already been in pending list";
                break;
            case PENDING:
                message = "successfully send request to admin";
                break;
            default:
                alertType = AlertType.ERROR;
                message = "internal error happened, check log";
                break;
        }
        new Alert(alertType, message, ButtonType.OK).showAndWait();
    }

    @FXML
    private void onUnregister(ActionEvent e) {
        LoggedUserResource resource = LoggedUserResource.getInstance();

        if (resource.isGuess()) {
            Alert dialog = new Alert(AlertType.ERROR, "you must login first!", ButtonType.OK);
            dialog.showAndWait();
            return;
        }

        UserRepository repository = new UserRepository(App.getSessionFactory().openSession());
        User user = resource.getUser();

        Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to unregister this meeting?", ButtonType.YES,
                ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            boolean result = App.getUnitOfWork().cancelMeeting(user, meeting);
            String message = result ? "Unregistered successfully"
                    : "An error happened and your request can not be unregistered";
            AlertType alertType = result ? AlertType.CONFIRMATION : AlertType.ERROR;
            new Alert(alertType, message, ButtonType.OK).showAndWait();
        }

        repository.close();
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