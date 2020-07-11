package com.azer.meetingmanager.ui.account;

import com.azer.meetingmanager.data.MeetingFilterOption;
import com.azer.meetingmanager.ui.DialogLoader;
import com.azer.meetingmanager.ui.OnCompleteListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountController {

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
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onEdit(ActionEvent event) {

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
}
