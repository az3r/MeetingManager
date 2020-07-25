package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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

    private static final int SORT_BY_NAME = 1;
    private static final int SORT_BY_EMAIL = 2;

    public static final int ACTION_NONE = 0;
    public static final int ACTION_BLOCK = 1;
    public static final int ACTION_REQUEST = 2;

    private int actionFlags = 0;

    @FXML
    private ScrollPane root;

    @FXML
    private VBox container;

    private Meeting meeting;

    private boolean blockAction;

    private final ObservableList<User> items = FXCollections.observableArrayList();

    private OnItemActionListener<User> acceptListener;

    private OnItemActionListener<User> denyListener;

    private int sortType = SORT_BY_NAME;
    private boolean ascendingSort = true;

    private List<Node> originalChildren;

    private final Comparator<Node> emailComparator = (left, right) -> {
        User leftData = (User) left.getUserData();
        User rightData = (User) right.getUserData();
        return leftData.getUserEmail().compareTo(rightData.getUserEmail());
    };

    private final Comparator<Node> nameComparator = (left, right) -> {
        User leftData = (User) left.getUserData();
        User rightData = (User) right.getUserData();
        return leftData.getUserName().compareTo(rightData.getUserName());
    };

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

    public void setItemActionType(int actionFlags) {
        this.actionFlags = actionFlags;
    }

    public void notifyCollectionChanged(Collection<User> collection) {
        this.items.setAll(collection);
        originalChildren = new ArrayList<>(container.getChildren());
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
        controller.showAcceptAction(actionFlags == ACTION_REQUEST);
        controller.showDenyAction(actionFlags == ACTION_REQUEST);
        controller.showBlockAction((actionFlags == ACTION_BLOCK) && !user.getBlocked());
        controller.showUnblockAction((actionFlags == ACTION_BLOCK) && user.getBlocked());

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

    public void sortByEmailAsc() {
        sortType = SORT_BY_EMAIL;
        ascendingSort = true;
        List<Node> sortedNodes = container.getChildren().sorted(emailComparator);
        container.getChildren().setAll(sortedNodes);
    }

    public void sortByNameAsc() {
        sortType = SORT_BY_NAME;
        ascendingSort = true;
        List<Node> sortedNodes = container.getChildren().sorted(nameComparator);
        container.getChildren().setAll(sortedNodes);
    }

    public void filterByNone() {
        List<Node> copied = originalChildren.stream().sorted(sortType == SORT_BY_NAME ? nameComparator : emailComparator).collect(Collectors.toList());
        container.getChildren().setAll(copied);
    }

    public void filterByBlocked() {
        List<Node> copied = new ArrayList<>(originalChildren);
        copied.removeIf(node -> {
            User user = (User) node.getUserData();
            return !user.getBlocked();
        });
        copied.sort(sortType == SORT_BY_NAME ? nameComparator : emailComparator);
        container.getChildren().setAll(copied);
    }

    public void filterByNotBlocked() {
        List<Node> copied = new ArrayList<>(originalChildren);
        copied.removeIf(node -> {
            User user = (User) node.getUserData();
            return user.getBlocked();
        });
        copied.sort(sortType == SORT_BY_NAME ? nameComparator : emailComparator);
        container.getChildren().setAll(copied);
    }

    public void sortByEmailDsc() {
        sortType = SORT_BY_EMAIL;
        ascendingSort = false;
        List<Node> sortedNodes = container.getChildren().sorted(emailComparator.reversed());
        container.getChildren().setAll(sortedNodes);
    }

    public void sortByNameDsc() {
        sortType = SORT_BY_NAME;
        ascendingSort = false;
        List<Node> sortedNodes = container.getChildren().sorted(nameComparator.reversed());
        container.getChildren().setAll(sortedNodes);
    }

}