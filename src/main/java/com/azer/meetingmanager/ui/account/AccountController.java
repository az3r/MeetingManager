package com.azer.meetingmanager.ui.account;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.AccountHelper;
import com.azer.meetingmanager.helpers.StringHelper;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AccountController extends BackableController implements Initializable {

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
    private Label userNameErrorLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label emailErrorLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label confirmErrorLabel;

    @FXML
    private MeetingContainerController meetingContainerController;

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

        userNameErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        confirmErrorLabel.setVisible(false);

        if (StringHelper.nullOrEmpty(fullNameTextField.getText())) {
            userNameErrorLabel.setVisible(true);
        } else if (StringHelper.nullOrEmpty(emailTextField.getText())) {
            emailErrorLabel.setVisible(true);
        } else if (!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
            confirmErrorLabel.setVisible(true);
        } else {

            // update in database
            User user = LoggedUserResource.getInstance().getUser();
            user.setUserName(fullNameTextField.getText());
            user.setUserEmail(emailTextField.getText());
            user.getAccount().setPassword(
                    AccountHelper.generatePassword(passwordTextField.getText(), user.getAccount().getSalt()));
            boolean success = App.getUnitOfWork().updateUser(LoggedUserResource.getInstance().getUser());

            // update in UI
            if (success) {
                fullNameLabel.setText(fullNameTextField.getText());
                emailLabel.setText(emailTextField.getText());
                showEditPane(false);
            } else {
                new Alert(AlertType.ERROR, "unable to update your account info", ButtonType.OK).showAndWait();
            }
        }
    }

    @FXML
    void onSortByLatest(ActionEvent event) {
        meetingContainerController.sortByLatest();
    }

    @FXML
    void onSortbyAlphabet(ActionEvent event) {
        meetingContainerController.sortByName();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupMeetingContainer();
        setupAccountInfo();
    };

    private void setupAccountInfo() {
        User user = LoggedUserResource.getInstance().getUser();
        fullNameLabel.setText(user.getUserName());
        fullNameTextField.setText(user.getUserName());

        emailLabel.setText(user.getUserEmail());
        emailTextField.setText(user.getUserEmail());
    }

    private void setupTopbar() {
        topbarController.setTitle("Account");
        topbarController.showSearchOption(true);
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            root.getScene().setRoot(getUpParent());
        });
        topbarController.setOnSearchAction(e -> {
            String query = topbarController.getSearchQuery();
            meetingContainerController.onFilter(query);
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
}
