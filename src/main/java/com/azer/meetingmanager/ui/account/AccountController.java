package com.azer.meetingmanager.ui.account;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AccountController implements Initializable {

    private static final String TAG = "AccountController";
    @FXML
    private TopbarController topbarController;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox infoVBox;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private VBox editVBox;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private MeetingContainerController meetingContainerController;

    private Parent previousParent;

    @FXML
    void onCancel(ActionEvent event) {
        showEditPane(false);
    }

    @FXML
    void onEdit(ActionEvent event) {
        showEditPane(true);
    }

    private void showEditPane(boolean visible) {
        editVBox.setVisible(visible);
        infoVBox.setVisible(!visible);
    }

    @FXML
    void onSave(ActionEvent event) {
        // TODO update in database

        // update in UI
        fullNameLabel.setText(fullNameTextField.getText());
        emailLabel.setText(emailTextField.getText());
        showEditPane(false);
    }

    @FXML
    void onSortByLatest(ActionEvent event) {

    }

    @FXML
    void onSortbyAlphabet(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupMeetingContainer();
    };

    /**
     * set the Preivous root node that navigates to this view
     */
    public void setPreviousNode(Parent previousNode) {
        this.previousParent = previousNode;
    }

    public Parent getPreviousParent() {
        return this.previousParent;
    }

    private void setupTopbar() {
        topbarController.setTitle("Account");
        topbarController.showSearchOption(true);
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            getScene().setRoot(getPreviousParent());
        });
        topbarController.setOnSearchAction(e -> {
            String query = topbarController.getSearchQuery();
            // TODO handle query
        });
    }

    private void setupMeetingContainer() {

        User user = LoggedUserResource.getInstance().getUser();
        Collection<Meeting> acceptedMeetings = App.getUnitOfWork().getAcceptedMeetings(user);

        meetingContainerController.setRightButtonText("Unregister");
        meetingContainerController.setRightButtonListener(meeting -> {
            Alert alert = new Alert(AlertType.WARNING,
                    "Are you sure you want to unregister this meeting? If you want to register this meeting again, you will have to wait for admin to accept",
                    ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().get();
            if (result == ButtonType.YES) {
                boolean success = App.getUnitOfWork().cancelMeeting(user, meeting);
                String message = success ? "Successfully unregister meeting" : "Internal error happened, check log!";
                AlertType alertType = success ? AlertType.INFORMATION : AlertType.ERROR;
                new Alert(alertType, message, ButtonType.OK).showAndWait();
            }
        });

        meetingContainerController.notifyCollectionChanged(acceptedMeetings);

    }

    private Scene getScene() {
        return root.getScene();
    }
}
