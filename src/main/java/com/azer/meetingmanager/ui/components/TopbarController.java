package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.account.AccountController;
import com.azer.meetingmanager.ui.admin.AdminHomeController;
import com.azer.meetingmanager.ui.admin.AdminUserController;
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

    private static final String TAG = "TopbarController";

    private static final int TOPBAR_GUESS = 0;
    private static final int TOPBAR_MEMBER = 1;
    private static final int TOPBAR_ADMIN = 2;

    @FXML
    private BorderPane root;

    @FXML
    private Button backButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    @FXML
    private Text titleText;

    @FXML
    private Button loginButton;

    @FXML
    private Button editButton;

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
    public void initialize(final URL location, final ResourceBundle resources) {

        // manage all children in action menu and title layout
        for (final Node childreNode : menuLayout.getChildren()) {
            childreNode.managedProperty().bind(childreNode.visibleProperty());
        }
        for (final Node childreNode : homeLayout.getChildren()) {
            childreNode.managedProperty().bind(childreNode.visibleProperty());
        }

        searchView.visibleProperty().bind(searchButton.visibleProperty());
        showBackButton(false);
        showSearchOption(false);
        showEditButton(false);

        final LoggedUserResource instance = LoggedUserResource.getInstance();
        if (instance.isGuess())
            setTopbarType(TOPBAR_GUESS);
        else if (instance.isMember())
            setTopbarType(TOPBAR_MEMBER);
        else
            setTopbarType(TOPBAR_ADMIN);
    }

    @FXML
    void onLogOut(final ActionEvent event) {
        final Optional<ButtonType> result = new Alert(AlertType.CONFIRMATION, "Are you sure you want to log out?",
                ButtonType.YES, ButtonType.NO).showAndWait();
        if (result.get() == ButtonType.YES) {
            LoggedUserResource.getInstance().logout();
            final ViewLoader<HomeController> loader = new ViewLoader<>("views/Home.fxml");
            root.getScene().setRoot(loader.getRoot());
        }
    }

    @FXML
    void onLogin(final ActionEvent event) {
        final DialogLoader<User> loader = new DialogLoader<>("views/Login.fxml", (Stage) root.getScene().getWindow());
        loader.showAndWait(loginCallback);
    }

    @FXML
    void onCreateMeeting(final ActionEvent event) {
        final DialogLoader<Meeting> dialog = new DialogLoader<>("views/MeetingEditor.fxml",
                (Stage) root.getScene().getWindow());
        dialog.showAndWait(createMeetingCallback);
    }

    @FXML
    void onOpenUserManager(final ActionEvent event) {
        final ViewLoader<AdminUserController> loader = new ViewLoader<>("views/AdminUser.fxml");
        loader.getController().setUpParent(root.getScene().getRoot());
        root.getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onOpenHome(final ActionEvent event) {
        String view = "views/Home.fxml";
        if (LoggedUserResource.getInstance().getAdmin() != null)
            view = "views/AdminHome.fxml";
        final ViewLoader<HomeController> loader = new ViewLoader<>(view);
        root.getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onOpenAccount(final ActionEvent event) {
        final ViewLoader<AccountController> loader = new ViewLoader<>("views/Account.fxml");
        loader.getController().setUpParent(root.getScene().getRoot());
        root.getScene().setRoot(loader.getRoot());
    }

    @FXML
    void onSignup(final ActionEvent event) {
        final DialogLoader<User> loader = new DialogLoader<>("views/Signup.fxml", (Stage) root.getScene().getWindow());
        loader.showAndWait(signUpCallback);
    }

    @FXML
    void onOpenMeetingManager(final ActionEvent event) {
        final ViewLoader<AccountController> loader = new ViewLoader<>("views/AdminMeeting.fxml");
        root.getScene().setRoot(loader.getRoot());
    }

    public void showBackButton(final boolean visible) {
        backButton.setVisible(visible);
    }

    public void showAddButton(final boolean visible) {
        addButton.setVisible(visible);
    }

    public void showLoginButton(final boolean visible) {
        loginButton.setVisible(visible);
    }

    public void showSignUpButton(final boolean visible) {
        signUpButton.setVisible(visible);
    }

    public void showAccountButton(final boolean visible) {
        accountButton.setVisible(visible);
    }

    public void showAdminMenuItem(final boolean visible) {
        adminMenuItem.setVisible(visible);
    }

    public void showSearchOption(final boolean visible) {
        searchButton.setVisible(visible);
    }

    public void showHomeButton(final boolean visible) {
        homeButton.setVisible(visible);
    }

    public void showEditButton(final boolean visible) {
        editButton.setVisible(visible);
    }

    public void setTitle(final String title) {
        titleText.setText(title);
    }

    public void setAddButtonAction(final EventHandler<ActionEvent> handler) {
        addButton.setOnAction(handler);
    }

    public void setOnBackAction(final EventHandler<ActionEvent> handler) {
        backButton.setOnAction(handler);
    }

    public void setOnSearchAction(final EventHandler<ActionEvent> handler) {
        searchButton.setOnAction(handler);
    }

    public void setOnEdithAction(final EventHandler<ActionEvent> handler) {
        editButton.setOnAction(handler);
    }

    public String getSearchQuery() {
        return searchView.getText();
    }

    public void setTopbarType(final int type) {
        showAccountButton(type != TOPBAR_GUESS);
        showSignUpButton(type == TOPBAR_GUESS);
        showLoginButton(type == TOPBAR_GUESS);
        showAdminMenuItem(type == TOPBAR_ADMIN);
        showAddButton(type == TOPBAR_ADMIN);
    }

    private final OnCompleteListener<User> loginCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(final User result) {
            System.out.println("Logged in " + result);
            if (result instanceof Admin) {
                final ViewLoader<AdminHomeController> loader = new ViewLoader<>("views/AdminHome.fxml");
                root.getScene().setRoot(loader.getRoot());
            } else {
                setTopbarType(TOPBAR_MEMBER);
            }
        }

        @Override
        public void onError(final Exception e) {
            System.err.println(e);

        }

        @Override
        public void onCancelled() {
            System.out.println("Cancelled login");
        }

    };
    private final OnCompleteListener<User> signUpCallback = new OnCompleteListener<User>() {

        @Override
        public void onCompleted(final User result) {
            setTopbarType(TOPBAR_MEMBER);
        }

        @Override
        public void onError(final Exception e) {
            System.err.println(e);

        }

        @Override
        public void onCancelled() {
            System.out.println("Cancelled sign up");
        }

    };

    private final OnCompleteListener<Meeting> createMeetingCallback = new OnCompleteListener<Meeting>() {

        @Override
        public void onCompleted(Meeting result) {
            boolean success = App.getUnitOfWork().createMeeting(result);
            String message = success ? "Create meeting successfully" : "Internal error, check log!";
            AlertType alertType = success ? AlertType.INFORMATION : AlertType.ERROR;
            new Alert(alertType, message, ButtonType.OK).showAndWait();
        }

        @Override
        public void onError(Exception e) {
            Log.e(TAG, e.toString());
        }

        @Override
        public void onCancelled() {
            Log.i(TAG, "cancelled create meeting");
        }
    };
}