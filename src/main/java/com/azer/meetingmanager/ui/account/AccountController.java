package com.azer.meetingmanager.ui.account;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.MeetingFilterOption;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;
import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountController implements Initializable {

    @FXML
    private TopbarController topbarController;

    @FXML
    private AnchorPane root;

    @FXML
    private VBox infoVBox;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private VBox editVBox;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField confirmPasswordTextField;

    @FXML 
    private MeetingContainerController meetingContainerController;

    private Parent previousParent;
    

    @FXML
    void onCancel(ActionEvent event) {
        showEditPane(false);
    }

    @FXML
    void onEdit(ActionEvent event) {
        showEditPane(true);
    }

    private void showEditPane(boolean visible) {
        editVBox.setVisible(visible);
        infoVBox.setVisible(!visible);
    }

    @FXML
    void onOpenFilter(MouseEvent event) {
        DialogLoader<MeetingFilterOption> loader = new DialogLoader<>("views/MeetingFilter.fxml", getStage());
        loader.showAndWait(filterCallback);
    }

    private Stage getStage() {
        return (Stage) infoVBox.getScene().getWindow();
    }

    @FXML
    void onSave(ActionEvent event) {
        // TODO update in database

        // update in UI
        fullNameLabel.setText(fullNameTextField.getText());
        emailLabel.setText(emailTextField.getText());
        showEditPane(false);
    }

    @FXML
    void onSortByLatest(ActionEvent event) {

    }

    @FXML
    void onSortbyAlphabet(ActionEvent event) {

    }

    private OnCompleteListener<MeetingFilterOption> filterCallback = new OnCompleteListener<>() {

        @Override
        public void onCompleted(MeetingFilterOption result) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onCancelled() {

        }

    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupMeetingContainer();
    };

    /**
     * set the Preivous root node that navigates to this view
     */
    public void setPreviousNode(Parent previousNode) {
        this.previousParent = previousNode;
    }

    public Parent getPreviousParent() {
        return this.previousParent;
    }

    private void setupTopbar() {
        topbarController.setTitle("Account");
        topbarController.showSearchOption(true);
        topbarController.showBackButton(true);
        topbarController.setOnBackAction(e -> {
            getScene().setRoot(getPreviousParent());
        });
    }

    private void setupMeetingContainer() {
        
    }

    private Scene getScene() {
        return root.getScene();
    }
}
