package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class UserItemController implements Initializable {

    @FXML
    private Text nameText;

    @FXML
    private Text emailText;

    @FXML
    private HBox actionContainer;

    @FXML
    private Button acceptButton;

    @FXML
    private Button denyButton;

    @FXML
    private Button blockButton;

    @FXML
    private Button unblockButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Node node : actionContainer.getChildren()) {
            node.managedProperty().bind(node.visibleProperty());
        }
    }

    public void notifyDataChanged(User user) {
        nameText.setText(user.getUserName());
        emailText.setText(user.getUserEmail());
    }

    public void showAcceptAction(boolean visible) {
        acceptButton.setVisible(visible);
    }

    public void showDenyAction(boolean visible) {
        denyButton.setVisible(visible);
    }

    public void showBlockAction(boolean visible) {
        blockButton.setVisible(visible);
    }

    public void showUnblockAction(boolean visible) {
        unblockButton.setVisible(visible);
    }

    public void setOnAcceptListener(EventHandler<ActionEvent> event) {
        acceptButton.setOnAction(event);
    }

    public void setOnDenyListener(EventHandler<ActionEvent> event) {
        denyButton.setOnAction(event);

    }

    public void setOnBlockListener(EventHandler<ActionEvent> event) {
        blockButton.setOnAction(event);

    }

    public void setOnUnblockListener(EventHandler<ActionEvent> event) {
        unblockButton.setOnAction(event);

    }
}
