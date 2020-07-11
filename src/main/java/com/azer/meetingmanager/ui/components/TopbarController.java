package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.account.AccountController;
import com.azer.meetingmanager.ui.home.HomeController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TopbarController implements Initializable {

    @FXML
    private BorderPane root;

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
        Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION, "Are you sure you want to log out?",
                ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.get() == ButtonType.YES) {
            useTopbarType(false);
        }
    }

    @FXML
    void onLogin(ActionEvent event) {
        DialogLoader<User> loader = new DialogLoader<>("views/Login.fxml", getStage());
        loader.showAndWait(loginCallback);
    }

    @FXML
    void onOpenHome(ActionEvent event) {
        ViewLoader<HomeController> loader = new  ViewLoader<>("views/Home.fxml", getParentRoot());
        getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onOpenAccount(ActionEvent event) {
        ViewLoader<AccountController> loader = new  ViewLoader<>("views/Account.fxml", getParentRoot());
        loader.getController().setPreviousNode(getParentRoot());
        getScene().setRoot(loader.getRoot());
    }


    @FXML
    void onSignup(ActionEvent event) {
        DialogLoader<User> loader = new DialogLoader<>("views/Signup.fxml", getStage());
        loader.showAndWait(signUpCallback);
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

    public void setOnSearchAction(EventHandler<ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    public String getSearchQuery() {
        return searchView.getText();
    }


    public Stage getStage(){
        return (Stage) getScene().getWindow();
    }

    public Scene getScene(){
        return root.getScene();
    }

    private Parent getParentRoot() {
        return getScene().getRoot();
    }


    /**
     * true = display logged in user topbar, false = display guess topbar
     */
    public void useTopbarType(boolean loggedUserTopbar) {
        showAccountButton(loggedUserTopbar);
        showSignUpButton(!loggedUserTopbar);
        showLoginButton(!loggedUserTopbar);
    }


    private OnCompleteListener<User> loginCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(User result) {
            useTopbarType(true);
        }

        @Override
        public void onError(Exception e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCancelled() {
            // TODO Auto-generated method stub

        }

    };
    private OnCompleteListener<User> signUpCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(User result) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onError(Exception e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onCancelled() {
            // TODO Auto-generated method stub

        }

    };
}