package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.detail.MeetingDetailController;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MeetingContainerController implements Initializable, ListChangeListener<Meeting> {

    @FXML
    private ScrollPane root;

    // propperties for each meeting item
    private String leftButtonText;
    private String rightButtonText;
    private EventHandler<ActionEvent> leftButtonAction;
    private EventHandler<ActionEvent> rightButtonAction;

    // container propperties
    private Pane container;
    private VBox vboxLayout = createVBoxContainer();
    private TilePane tilePaneLayout = createTilePaneContainer();
    private String file = "views/MeetingItemHBox.fxml";
    private ObservableList<Meeting> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupItemDefault();
        changeContainerPane(true);

        // TODO remove code below when using populating data in database
        items.addListener(this);
        items.setAll(Arrays.asList(new Meeting(), new Meeting(), new Meeting(), new Meeting(), new Meeting(),
                new Meeting()));
        items.remove(2, 4);
    }

    /**
     * Default behavior of each meeting item
     */
    private void setupItemDefault() {
        setItemLeftButtonText("Detail");
        setItemRightButtonText("Register");
        setItemLeftButtonAction(e -> {
            ViewLoader<MeetingDetailController> loader = new ViewLoader<>("views/MeetingDetail.fxml",
                    root.getScene().getRoot());
            loader.getController().setPreviousParent(loader.getPreviousParent());
            root.getScene().setRoot(loader.getRoot());
        });
        setItemRightButtonAction(e -> {

        });
    }

    private void bindItemBehavior(MeetingItemController controller) {
        controller.setLeftButtonText(leftButtonText);
        controller.setRightButtonText(rightButtonText);
        controller.setLeftButtonAction(leftButtonAction);
        controller.setRightButtonAction(rightButtonAction);
    }

    private Parent createMeetingItemNode(Meeting meeting) {
        Parent node = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(file));
            node = loader.load();
            MeetingItemController controller = loader.getController();
            bindItemBehavior(controller);
            controller.notifyDataChanged(meeting);

        } catch (Exception e) {
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }

        return node;
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

    public void setCollection(Collection<Meeting> collection) {
        this.items.setAll(collection);
    }

    public void setItemLeftButtonText(String text) {
        this.leftButtonText = text;
    }

    public void setItemRightButtonText(String text) {
        this.rightButtonText = text;
    }

    public void setItemLeftButtonAction(EventHandler<ActionEvent> handler) {
        this.leftButtonAction = handler;
    }

    public void setItemRightButtonAction(EventHandler<ActionEvent> handler) {
        this.rightButtonAction = handler;
    }

    private void addToContainer(List<? extends Meeting> collection) {
        for (Meeting meeting : items) {
            container.getChildren().add(createMeetingItemNode(meeting));
        }
    }

    private void removeFromContainer(int fromInclusive, int size) {
    }

    @Override
    public void onChanged(Change<? extends Meeting> c) {
        c.next();

        if (c.wasAdded()) {
            addToContainer(c.getAddedSubList());
        } else if (c.wasRemoved()) {
            removeFromContainer(c.getFrom(), c.getRemovedSize());
        }
        // update list in UI
        // inflateContainer();
    }
}