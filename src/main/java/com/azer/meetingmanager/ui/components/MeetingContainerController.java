package com.azer.meetingmanager.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    private Pane container = createVBoxContainer();
    private String file = "views/MeetingItemHBox.fxml";
    private ObservableList<Meeting> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items.addListener(this);
        items.setAll(Arrays.asList(new Meeting(), new Meeting(), new Meeting(), new Meeting(), new Meeting(),
                new Meeting()));
        items.addAll(Arrays.asList(new Meeting(), new Meeting()));
    }

    /**
     * switch between two display styles: VBox or TilePane
     * 
     * @param vbox true = use VBox, otherwise use TilePane
     */
    public void changeContainerPane(boolean vbox) {

        // HBox item for VBox container; VBox item for TilePane container
        container = vbox ? createVBoxContainer() : createTilePaneContainer();
        file = vbox ? "views/MeetingItemHBox.fxml" : "views/MeetingItemVBox.fxml";
    }

    private VBox createVBoxContainer() {
        VBox container = new VBox();
        return container;
    }

    private TilePane createTilePaneContainer() {
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

    private void inflateContainer() {
        for (Meeting meeting : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(file));
                Parent itemRoot = loader.load();
                MeetingItemController controller = loader.getController();
                controller.notifyDataChanged(meeting);
                container.getChildren().add(itemRoot);

            } catch (IOException e) {
                System.err.println("Unable to load MeetingItem.fxml");
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    @Override
    public void onChanged(Change<? extends Meeting> c) {
        c.next();
        System.out.println(String.format("from %d to %d", c.getFrom(), c.getTo()));
        // update list in UI
        // inflateContainer();
    }
}