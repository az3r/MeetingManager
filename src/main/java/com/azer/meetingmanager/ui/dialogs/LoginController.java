package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginController extends DialogController<User> {

    @FXML
    private VBox root;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void onCancel(ActionEvent event) {
        cancelled();
        getContainer().close();
    }

    @FXML
    void onLogin(ActionEvent event) {
        LoggedUserResource resource = LoggedUserResource.getInstance();
        User loggedUser = resource.login(userNameTextField.getText(), passwordTextField.getText());
        if (loggedUser != null) {
            if (!loggedUser.getBlocked()) {
                success(resource.getUser());
                getContainer().close();
            } else {
                LoggedUserResource.getInstance().logout();
                new Alert(AlertType.INFORMATION,
                        "Your account is blocked by admin. Please contact admin for more information", ButtonType.OK)
                                .showAndWait();
            }
        } else {
            errorLabel.setText("*account or password is incorrect");
            errorLabel.setVisible(true);
        }
    }
}