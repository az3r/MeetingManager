package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignupController extends DialogController<User> {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    void onCancel(ActionEvent event) {
        setState(STATE_CANCELLED);
        
        getContainer().close();
    }

    @FXML
    void onRegister(ActionEvent event) {
        User user = new User();
        user.setName(userNameTextField.getText());

        setResult(user);
        setState(STATE_COMPLETED);
        
        getContainer().close();
    }
}
