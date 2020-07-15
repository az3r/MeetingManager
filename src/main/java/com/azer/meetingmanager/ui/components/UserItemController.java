package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class UserItemController implements Initializable {

    @FXML
    private Text nameText;

    @FXML
    private Text emailText;

    @FXML
    private HBox actionContainer;

    @FXML
    private VBox acceptAction;

    @FXML
    private VBox denyAction;

    @FXML
    private VBox blockAction;

    @FXML
    private VBox unblockAction;

    private User user;
    private Meeting meeting;


    @FXML
    void onAcceptRequest(MouseEvent event) {
        App.getUnitOfWork().acceptRequest(user, meeting);
    }  

    @FXML
    void onBlockUser(MouseEvent event) {

    }

    @FXML
    void onDenyRequest(MouseEvent event) {

    }

    @FXML
    void onUnBlockUser(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Node node : actionContainer.getChildren()) {
            node.managedProperty().bind(node.visibleProperty());
        }
    }

    public void notifyDataChanged(User user) {
        this.user = user;
        nameText.setText(user.getUserName());
        emailText.setText(user.getUserEmail());
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public void showAcceptAction(boolean visible) {
        acceptAction.setVisible(visible);
    }

    public void showDenyAction(boolean visible) {
        denyAction.setVisible(visible);
    }

    public void showBlockAction(boolean visible) {
        blockAction.setVisible(visible);
    }

    public void showUnblockAction(boolean visible) {
        unblockAction.setVisible(visible);
    }

}
