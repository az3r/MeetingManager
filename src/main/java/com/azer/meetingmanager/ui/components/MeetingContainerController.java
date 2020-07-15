package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.StringHelper;
import com.azer.meetingmanager.ui.OnItemActionListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.detail.MeetingDetailController;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MeetingContainerController implements Initializable, ListChangeListener<Meeting> {

    private static final String TAG = "MeetingContainerController";
    @FXML
    private ScrollPane root;

    // propperties for each meeting item
    private String leftButtonText;
    private String rightButtonText;
    private OnItemActionListener<Meeting> leftButtonListener;
    private OnItemActionListener<Meeting> rightButtonListener;

    // container propperties
    private Pane container;
    private VBox vboxLayout = createVBoxContainer();
    private TilePane tilePaneLayout = createTilePaneContainer();
    private String file = "views/MeetingItemHBox.fxml";
    private ObservableList<Meeting> items = FXCollections.observableArrayList();
    private List<Node> originalChildren = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupItemDefault();
        changeContainerPane(true);
        items.addListener(this);
    }

    /**
     * Default behavior of each meeting item
     */
    private void setupItemDefault() {
        setLeftButtonText("Detail");
        setRightButtonText("Register");
        setLeftButtonListener(meeting -> {
            ViewLoader<MeetingDetailController> loader = new ViewLoader<>("views/MeetingDetail.fxml");
            loader.getController().setUpParent(root.getScene().getRoot());
            loader.getController().notifyDataChanged(meeting);
            root.getScene().setRoot(loader.getRoot());
        });
        setRightButtonListener(meeting -> {
            LoggedUserResource resource = LoggedUserResource.getInstance();
            if (resource.isGuess()) {
                new Alert(AlertType.ERROR, "You must login first!", ButtonType.OK).showAndWait();
                return;
            }
            User user = resource.getUser();

            String message = "";
            AlertType alertType = AlertType.CONFIRMATION;
            switch (App.getUnitOfWork().registerMeeting(user, meeting)) {
                case ACCEPTED:
                    message = "You have already joined this meeting";
                    break;
                case ALREADY_PENDING:
                    message = "Your request is currently in pending list";
                    break;
                case PENDING:
                    message = "Successfully send request to admin";
                    break;
                default:
                    message = "Internal server error, please check log";
                    alertType = AlertType.ERROR;
                    break;
            }
            new Alert(alertType, message, ButtonType.OK).showAndWait();
        });
    }

    private void bindItemBehavior(MeetingItemController controller, Meeting value) {
        controller.setLeftButtonText(leftButtonText);
        controller.setRightButtonText(rightButtonText);
        controller.setLeftButtonAction(leftButtonListener, value);
        controller.setRightButtonAction(rightButtonListener, value);
    }

    private Parent createMeetingItemNode(Meeting meeting) {
        ViewLoader<MeetingItemController> loader = new ViewLoader<>(file);
        MeetingItemController controller = loader.getController();
        bindItemBehavior(controller, meeting);
        controller.notifyDataChanged(meeting);
        return loader.getRoot();
    }

    public void changeContainerPane(boolean vbox) {
        container = vbox ? vboxLayout : tilePaneLayout;
        file = vbox ? "views/MeetingItemHBox.fxml" : "views/MeetingItemVBox.fxml";
        if (container.getChildren().isEmpty())
            addToContainer(items);
        root.setContent(container);
    }

    private static VBox createVBoxContainer() {
        VBox container = new VBox();
        return container;
    }

    private static TilePane createTilePaneContainer() {
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER_LEFT);
        tilePane.setHgap(30.0);
        tilePane.setVgap(60.0);
        tilePane.setPadding(new Insets(30));
        return tilePane;
    }

    public void notifyCollectionChanged(Collection<Meeting> collection) {
        this.items.setAll(collection);
    }

    public void setLeftButtonText(String text) {
        this.leftButtonText = text;
    }

    public void setRightButtonText(String text) {
        this.rightButtonText = text;
    }

    public void setLeftButtonListener(OnItemActionListener<Meeting> listener) {
        this.leftButtonListener = listener;
    }

    public void setRightButtonListener(OnItemActionListener<Meeting> listener) {
        this.rightButtonListener = listener;
    }

    public void sortByName() {
        List<Node> sortedNodes = container.getChildren().sorted((left, right) -> {
            Meeting leftData = (Meeting) left.getUserData();
            Meeting rightData = (Meeting) right.getUserData();
            return leftData.getName().compareTo(rightData.getName());
        });
        container.getChildren().setAll(sortedNodes);
    }

    public void sortByLatest() {
        List<Node> sortedNodes = container.getChildren().sorted((left, right) -> {
            Meeting leftData = (Meeting) left.getUserData();
            Meeting rightData = (Meeting) right.getUserData();
            int compareResult = leftData.getHoldTime().compareTo(rightData.getHoldTime());
            if (compareResult > 0)
                compareResult = -1;
            else if (compareResult < 0)
                compareResult = 1;
            return compareResult;
        });
        container.getChildren().setAll(sortedNodes);
    }

    private void addToContainer(List<? extends Meeting> collection) {
        Log.i(TAG, "adding new meetings to container");
        for (Meeting meeting : items) {
            Log.i(TAG, meeting.toString());
            Parent node = createMeetingItemNode(meeting);
            node.setUserData(meeting);
            container.getChildren().add(node);
        }
    }

    private void removeFromContainer(int fromInclusive, int size) {
        Log.i(TAG, "removing meetings from container");
        container.getChildren().remove(fromInclusive, fromInclusive + size);
    }

    @Override
    public void onChanged(Change<? extends Meeting> c) {
        c.next();
        if (c.wasAdded()) {
            addToContainer(c.getAddedSubList());
        } else if (c.wasRemoved()) {
            removeFromContainer(c.getFrom(), c.getRemovedSize());
        }
    }

    public void onFilter(String query) {
        // store original collection
        if (originalChildren == null)
            originalChildren = container.getChildren().stream().collect(Collectors.toList());

        if (StringHelper.nullOrEmpty(query)) {
            container.getChildren().setAll(originalChildren);
            return;
        }

        // copy to another collection and use this for filtering
        List<Node> copied = originalChildren.stream().collect(Collectors.toList());
        copied.removeIf(node -> {
            Meeting nodeData = (Meeting) node.getUserData();
            return !nodeData.getName().toLowerCase().contains(query.toLowerCase());
        });
        if (copied.isEmpty()) {
            copied = originalChildren.stream().collect(Collectors.toList());
            copied.removeIf(node -> {
                Meeting nodeData = (Meeting) node.getUserData();
                return !nodeData.getLocation().getName().toLowerCase().contains(query.toLowerCase());
            });
        }
        if (copied.isEmpty()) {
            copied = originalChildren.stream().collect(Collectors.toList());
            copied.removeIf(node -> {
                Meeting nodeData = (Meeting) node.getUserData();
                return !nodeData.getLocation().getAddress().toLowerCase().contains(query.toLowerCase());
            });
        }
        container.getChildren().setAll(copied);
    }
}