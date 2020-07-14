package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        if (resource.login(userNameTextField.getText(), passwordTextField.getText())) {
            success(resource.getUser());
            getContainer().close();

        } else {
            errorLabel.setText("*account or password is incorrect");
            errorLabel.setVisible(true);
        }
    }
}