package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.login.LoggedUserResource;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.account.AccountController;
import com.azer.meetingmanager.ui.admin.AdminMeetingController;
import com.azer.meetingmanager.ui.home.HomeController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TopbarController implements Initializable {

    private static final int TOPBAR_GUESS = 0;
    private static final int TOPBAR_MEMBER = 1;
    private static final int TOPBAR_ADMIN = 2;

    @FXML
    private BorderPane root;

    @FXML
    private Button backButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    @FXML
    private Text titleText;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;

    @FXML
    private MenuButton accountButton;

    @FXML
    private MenuItem adminMenuItem;

    @FXML
    private TextField searchView;

    @FXML
    private HBox menuLayout;

    @FXML
    private HBox homeLayout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // manage all children in action menu and title layout
        for (Node childreNode : menuLayout.getChildren()) {
            childreNode.managedProperty().bind(childreNode.visibleProperty());
        }
        for (Node childreNode : homeLayout.getChildren()) {
            childreNode.managedProperty().bind(childreNode.visibleProperty());
        }

        searchView.visibleProperty().bind(searchButton.visibleProperty());
        showAddButton(false);
        showBackButton(false);

        LoggedUserResource instance = LoggedUserResource.getInstance();
        if (instance.isGuess())
            setTopbarType(TOPBAR_GUESS);
        else if (instance.isMember())
            setTopbarType(TOPBAR_MEMBER);
        else
            setTopbarType(TOPBAR_ADMIN);
    }

    @FXML
    void onLogOut(ActionEvent event) {
        Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION, "Are you sure you want to log out?",
                ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.get() == ButtonType.YES)
            setTopbarType(TOPBAR_GUESS);
    }

    @FXML
    void onLogin(ActionEvent event) {
        DialogLoader<User> loader = new DialogLoader<>("views/Login.fxml", (Stage) root.getScene().getWindow());
        loader.showAndWait(loginCallback);
    }

    @FXML
    void onOpenHome(ActionEvent event) {
        ViewLoader<HomeController> loader = new ViewLoader<>("views/Home.fxml", root.getScene().getRoot());
        root.getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onOpenAccount(ActionEvent event) {
        ViewLoader<AccountController> loader = new ViewLoader<>("views/Account.fxml", root.getScene().getRoot());
        loader.getController().setPreviousNode(root.getScene().getRoot());
        root.getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onSignup(ActionEvent event) {
        DialogLoader<User> loader = new DialogLoader<>("views/Signup.fxml", (Stage) root.getScene().getWindow());
        loader.showAndWait(signUpCallback);
    }

    @FXML
    void onOpenMeetingManager(ActionEvent event) {
        ViewLoader<AccountController> loader = new ViewLoader<>("views/AdminMeeting.fxml");
        root.getScene().setRoot(loader.getRoot());
    }

    public void showBackButton(boolean visible) {
        backButton.setVisible(visible);
    }

    public void showAddButton(boolean visible) {
        addButton.setVisible(visible);
    }

    public void setAddButtonAction(EventHandler<ActionEvent> handler) {
        addButton.setOnAction(handler);
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

    public void showAdminMenuItem(boolean visible) {
        adminMenuItem.setVisible(visible);
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

    public void setTopbarType(int type) {
        showAccountButton(type != TOPBAR_GUESS);
        showSignUpButton(type == TOPBAR_GUESS);
        showLoginButton(type == TOPBAR_GUESS);
        showAdminMenuItem(type == TOPBAR_ADMIN);
    }

    private OnCompleteListener<User> loginCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(User result) {
            System.out.println("Logged in " + result);
            if (result instanceof Admin) {
                ViewLoader<AdminMeetingController> loader = new ViewLoader<>("views/AdminMeeting.fxml");
                root.getScene().setRoot(loader.getRoot());
            } else {
                setTopbarType(TOPBAR_MEMBER);
            }
        }

        @Override
        public void onError(Exception e) {
            System.err.println(e);

        }

        @Override
        public void onCancelled() {
            System.out.println("Cancelled login");
        }

    };
    private OnCompleteListener<User> signUpCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(User result) {
            setTopbarType(TOPBAR_MEMBER);
        }

        @Override
        public void onError(Exception e) {
            System.err.println(e);

        }

        @Override
        public void onCancelled() {
            System.out.println("Cancelled sign up");
        }

    };
}