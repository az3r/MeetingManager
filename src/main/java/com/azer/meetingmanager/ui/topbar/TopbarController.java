package com.azer.meetingmanager.ui.topbar;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.login.LoginController;
import com.azer.meetingmanager.ui.login.LoginDialog;
import com.azer.meetingmanager.ui.signup.SignupController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TopbarController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

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

    private Parent loginRoot;
    private LoginController loginController;

    private Parent signupRoot;
    private SignupController signupController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.managedProperty().bind(backButton.visibleProperty());
        loginButton.managedProperty().bind(loginButton.visibleProperty());
        signUpButton.managedProperty().bind(signUpButton.visibleProperty());
        accountButton.managedProperty().bind(accountButton.visibleProperty());
        searchButton.managedProperty().bind(searchButton.visibleProperty());
        searchView.visibleProperty().bind(searchButton.visibleProperty());
    }

    @FXML
    void onLogOut(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Login.fxml"));
            loginRoot = loader.load();
            this.loginController = loader.getController();

        } catch (Exception e) {
            System.err.println(e);
        }

        if (loginController != null && loginRoot != null) {
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(loginRoot));
            stage.initOwner(backButton.getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            useTopbarType(loginController.getUser() != null);
        }
    }

    @FXML
    void onOpenAccount(ActionEvent event) {

    }

    @FXML
    void onSignup(ActionEvent event) {

    }

    /**
     * open signup or login dialog
     * 
     * @param loginDialog true = open login dialog, false = open signup dialog
     */
    private void openDialog(boolean loginDialog) {

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

    public void showSearchOption(boolean visible) {
        searchButton.setVisible(visible);
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public void setOnBackAction(EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }

    /**
     * true = display logged in user topbar, false = display guess topbar
     */
    public void useTopbarType(boolean loggedUserTopbar) {
        showAccountButton(loggedUserTopbar);
        showSignUpButton(!loggedUserTopbar);
        showLoginButton(!loggedUserTopbar);
    }

    public void showLoginDialog() {

    }
}