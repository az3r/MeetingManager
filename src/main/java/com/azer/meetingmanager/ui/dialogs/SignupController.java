package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {

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


    private User user;

    @FXML
    void onCancel(ActionEvent event) {
        this.user = null;
        Stage stage = (Stage) userNameTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onRegister(ActionEvent event) {
        user = new User();
        user.setName(userNameTextField.getText());
        Stage stage = (Stage) userNameTextField.getScene().getWindow();
        stage.close();
    }

        /**
     * call this function after closing LoginDialog to get dialog's result 
     * <p>if {@link LoginController#onCancel()} is called before this function then null is returned</p>
     * <p>if {@link LoginController#onLogin()} is called before this function then an {@link User} instance is returned when all inputs are valid, otherwise null is returned</p>
     * 
     * @return either null or an instance of {@link User}
     */
    public User getUser(){
        return user;
    }

}
