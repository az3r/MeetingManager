package com.azer.meetingmanager.ui.topbar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class TopbarController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Text titleText;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private MenuButton accountButton;

    @FXML
    private TextField searchView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.managedProperty().bind(backButton.visibleProperty());
        loginButton.managedProperty().bind(loginButton.visibleProperty());
        signUpButton.managedProperty().bind(signUpButton.visibleProperty());
        accountButton.managedProperty().bind(accountButton.visibleProperty());

    }

    public void showBackButton(boolean visible) {
        backButton.setVisible(visible);
    }

    public void showLoginButton(boolean visible) {
        loginButton.setVisible(visible);
    }

    public void showSignUpButton(boolean visible) {
        signUpButton.setVisible(visible);
    }

    public void showAccountButton(boolean visible) {
        accountButton.setVisible(visible);
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

        /**
     * true = display logged in user topbar,
     * false = display guess topbar
     */
    public void useLoggedUserTopbar(boolean loggedUserTopbar) {
        showAccountButton(loggedUserTopbar);
        showSignUpButton(!loggedUserTopbar);
        showLoginButton(!loggedUserTopbar);
    }
}