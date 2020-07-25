package com.azer.meetingmanager.ui.detail;

import java.net.URL;
import java.text.DateFormat;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.helpers.Utility;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.dialogs.MeetingEditorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class MeetingDetailController extends BackableController implements Initializable {

    protected static final String TAG = "MeetingDetailController";

    @FXML
    private Parent root;

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
    private HBox actionHBox;

    @FXML
    private StackPane photoEmptyPane;

    private Meeting meeting;

    public void notifyDataChanged(Meeting meeting) {
        this.meeting = meeting;

        int acceptedCount = App.getUnitOfWork().countAcceptedUsers(meeting.getMeetingId());
        int pendingCount = App.getUnitOfWork().countPendingUsers(meeting.getMeetingId());

        titleLabel.setText(meeting.getName());
        if (meeting.getEnded())
            titleLabel.setTextFill(Paint.valueOf("red"));
        
        detailLabel.setText(meeting.getDetailDesc());

        String capacity = String.format("%d / %d", acceptedCount, meeting.getLocation().getCapacity());
        capacityLabel.setText(capacity);

        String date = DateFormat.getDateTimeInstance().format(meeting.getHoldTime());
        timeLabel.setText(date);

        String location = String.format("%s - %s", meeting.getLocation().getName(), meeting.getLocation().getAddress());
        locationLabel.setText(location);

        pendingLabel.setText(String.valueOf(pendingCount));

        boolean hasPhoto = meeting.getPhoto() != null;
        photoEmptyPane.setVisible(!hasPhoto);
        photoImageView.setVisible(hasPhoto);

        if (hasPhoto)
            photoImageView.setImage(Utility.getImage(meeting.getPhoto()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupPhotoPane();

        if (LoggedUserResource.getInstance().isAdmin())
            showActionButton(false);
    }

    private void setupPhotoPane() {
        photoEmptyPane.prefWidthProperty().bind(photoImageView.fitWidthProperty());
        photoEmptyPane.prefHeightProperty().bind(photoImageView.fitHeightProperty());
    }

    private void setupTopbar() {
        setupTopbar(LoggedUserResource.getInstance().isAdmin());
    }

    private void setupTopbar(boolean admin) {
        topbarController.setTitle("Detail");
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            timeLabel.getScene().setRoot(getUpParent());
        });

        topbarController.showEditButton(admin);
        topbarController.setOnEdithAction(e -> {
            if (this.meeting.getEnded()) {
                new Alert(AlertType.INFORMATION, "You can not edit this meeting because it has ended", ButtonType.OK)
                        .showAndWait();
                return;
            }
            DialogLoader<Meeting> loader = new DialogLoader<>("views/MeetingEditor.fxml",
                    (Stage) root.getScene().getWindow(), "Edit meeting");
            MeetingEditorController controller = (MeetingEditorController) loader.getController();
            controller.notifyDataChanged(this.meeting);
            loader.showAndWait(meetingEditCallback);
        });
    }

    public void showActionButton(boolean visible) {
        actionHBox.setVisible(visible);
    }

    @FXML
    private void onOpenAcceptedList(ActionEvent e) {
        ViewLoader<AcceptedListController> loader = new ViewLoader<>("views/AcceptedList.fxml");
        loader.getController().notifyDataChanged(this.meeting);
        root.getScene().setRoot(loader.getRoot());
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

    private OnCompleteListener<Meeting> meetingEditCallback = new OnCompleteListener<Meeting>() {

        @Override
        public void onCompleted(Meeting result) {
            boolean success = App.getUnitOfWork().updateMeeting(result);

            if (success)
                notifyDataChanged(result);

            String message = success ? "Update meeting successfully" : "Internal error, check log!";
            AlertType alertType = success ? AlertType.INFORMATION : AlertType.ERROR;
            new Alert(alertType, message, ButtonType.OK).showAndWait();
        }

        @Override
        public void onError(Exception e) {
            Log.e(TAG, e.toString());
        }

        @Override
        public void onCancelled() {
            Log.i(TAG, "canceled edit meeting");

        }
    };
}