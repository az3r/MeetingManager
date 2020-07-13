package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SignupController extends DialogController<User> {

    @FXML
    private TextField accountNameTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Label accountErrorLabel;

    @FXML
    void onCancel(ActionEvent event) {
        setState(STATE_CANCELLED);
        getContainer().close();
    }

    @FXML
    void onRegister(ActionEvent event) {
        if (confirmPasswordTextField.getText().equals(passwordTextField.getText())) {
            LoggedUserResource resource = LoggedUserResource.getInstance();
            if (resource.register(userNameTextField.getText(), emailTextField.getText(), accountNameTextField.getText(),
                    passwordTextField.getText())) {
                setState(STATE_COMPLETED);
                setResult(resource.getUser());
                getContainer().close();
            } else {
                accountErrorLabel.setText("*this name already existed");
                accountErrorLabel.setVisible(true);
            }
        } else {
            errorLabel.setText("*confirm password does not match");
            errorLabel.setVisible(true);
        }

    }
}
