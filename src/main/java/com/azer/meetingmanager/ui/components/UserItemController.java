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

    private OnUserRequestListener acceptListener;
    private OnUserRequestListener denyListener;
    private OnUserRequestListener blockListener;
    private OnUserRequestListener unblockListener;

    @FXML
    void onAcceptRequest(MouseEvent event) {
        if (denyListener != null) {
            denyListener.onAction(user, meeting);
        }
    }

    @FXML
    void onBlockUser(MouseEvent event) {
        if (denyListener != null) {
            denyListener.onAction(user, meeting);
        }
    }

    @FXML
    void onDenyRequest(MouseEvent event) {
        if (blockListener != null) {
            blockListener.onAction(user, meeting);
        }
    }

    @FXML
    void onUnBlockUser(MouseEvent event) {
        if (unblockListener != null) {
            unblockListener.onAction(user, meeting);
        }
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

    public void setOnAcceptListener(OnUserRequestListener listener) {
        this.acceptListener = listener;
    }

    public void setOnDenyListener(OnUserRequestListener listener) {
        this.denyListener = listener;
    }

    public void setOnBlockListener(OnUserRequestListener listener) {
        this.blockListener = listener;
    }

    public void setOnUnblockListener(OnUserRequestListener listener) {
        this.unblockListener = listener;
    }

    public interface OnUserRequestListener {
        void onAction(User user, Meeting meeting);
    }
}
