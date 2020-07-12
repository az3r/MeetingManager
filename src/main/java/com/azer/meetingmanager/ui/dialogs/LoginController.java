package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    void onCancel(ActionEvent event) {
        setState(STATE_CANCELLED);

        getContainer().close();
    }

    @FXML
    void onLogin(ActionEvent event) {
        
        User user = new User();

        setResult(user);
        setState(STATE_COMPLETED);
        
        getContainer().close();
    }
}