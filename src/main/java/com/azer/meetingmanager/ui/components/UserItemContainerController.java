package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.OnItemActionListener;
import com.azer.meetingmanager.ui.ViewLoader;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class UserItemContainerController implements Initializable, ListChangeListener<User> {

    private static final String TAG = "UserItemContainerController";

    @FXML
    private ScrollPane root;

    @FXML
    private VBox container;

    private Meeting meeting;

    private boolean blockAction;

    private ObservableList<User> items = FXCollections.observableArrayList();

    private OnItemActionListener<User> acceptListener;

    private OnItemActionListener<User> denyListener;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.addListener(this);
    }

    private void addToContainer(List<? extends User> collection) {
        Log.i(TAG, "adding new user to container");
        for (User user : items) {
            Log.i(TAG, user.toString());
            Parent node = createItemLayout(user);
            if (node != null) {
                node.setUserData(user);
                container.getChildren().add(node);
            }

        }
    }

    private void removeFromContainer(int fromInclusive, int size) {
        Log.i(TAG, "removing user from container");
        container.getChildren().remove(fromInclusive, fromInclusive + size);
    }

    @Override
    public void onChanged(Change<? extends User> c) {
        c.next();
        if (c.wasAdded()) {
            addToContainer(c.getAddedSubList());
        } else if (c.wasRemoved()) {
            removeFromContainer(c.getFrom(), c.getRemovedSize());
        }
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public void setItemActionType(boolean blockAction) {
        this.blockAction = blockAction;
    }

    public void notifyCollectionChanged(Collection<User> collection) {
        this.items.setAll(collection);
    }

    public void setOnAcceptListener(OnItemActionListener<User> listener) {
        this.acceptListener = listener;
    }

    public void setOnDenyListener(OnItemActionListener<User> listener) {
        this.denyListener = listener;
    }

    private Parent createItemLayout(User user) {
        if (!blockAction && user.getBlocked())
            return null;

        ViewLoader<UserItemController> loader = new ViewLoader<>("views/UserItem.fxml");
        UserItemController controller = loader.getController();
        setupItemController(controller, user, loader.getRoot());
        return loader.getRoot();
    }

    private void setupItemController(UserItemController controller, User user, Node owner) {
        controller.notifyDataChanged(user);
        controller.showAcceptAction(!blockAction);
        controller.showDenyAction(!blockAction);
        controller.showBlockAction(blockAction && !user.getBlocked());
        controller.showUnblockAction(blockAction && user.getBlocked());

        controller.setOnAcceptListener(e -> {
            if (meeting == null) {
                Log.e(TAG, "No meeting to accept request of " + user);
                return;
            }
            boolean success = App.getUnitOfWork().acceptRequest(user, meeting);
            if (success) {
                if (acceptListener != null)
                    acceptListener.onAction(user);
                container.getChildren().remove(owner);
            } else
                new Alert(AlertType.ERROR, "Unable to remove item from container, check log", ButtonType.OK)
                        .showAndWait();
        });
        controller.setOnDenyListener(e -> {
            if (meeting == null) {
                if (denyListener != null)
                    denyListener.onAction(user);
                Log.e(TAG, "No meeting to deny request of " + user);
                return;
            }
            boolean success = App.getUnitOfWork().denyRequest(user, meeting);
            if (success)
                container.getChildren().remove(owner);
            else
                new Alert(AlertType.ERROR, "Unable to remove item from container, check log", ButtonType.OK)
                        .showAndWait();
        });

        controller.setOnBlockListener(e -> {
            boolean success = App.getUnitOfWork().blockUser(user);
            controller.showUnblockAction(success);
            controller.showBlockAction(!success);

            if (!success)
                new Alert(AlertType.ERROR, "Unable to block user, check log", ButtonType.OK).showAndWait();
        });
        controller.setOnUnblockListener(e -> {
            boolean success = App.getUnitOfWork().unblockUser(user);
            controller.showBlockAction(success);
            controller.showUnblockAction(!success);

            if (!success)
                new Alert(AlertType.ERROR, "Unable to block user, check log", ButtonType.OK).showAndWait();
        });

    }

}