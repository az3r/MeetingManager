package com.azer.meetingmanager.ui.containers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.items.MeetingItemController;
import com.azer.meetingmanager.ui.overlay.OverlayController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MeetingContainerController implements Initializable {

    @FXML
    private ScrollPane root;

    private ObservableList<Meeting> items = FXCollections.emptyObservableList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * switch between two display styles: VBox or TilePane
     * 
     * @param vbox true = use VBox, otherwise use TilePane
     */
    public void changeContainerPane(boolean vbox) {

        root.getChildrenUnmodifiable().clear();

        // HBox item for VBox container; VBox item for TilePane container
        Pane container = vbox ? createVBoxContainer() : createTilePaneContainer();
        String file = vbox ? "views/MeetingItemHBox" : "views/MeetingItemVBox";

        for (Meeting meeting : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(file));
                loader.<MeetingItemController>getController().notifyDataChanged(meeting);
                Parent itemRoot = loader.load();
                container.getChildren().add(itemRoot);

            } catch (IOException e) {
                System.err.println("Unable to load MeetingItem.fxml");
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private VBox createVBoxContainer() {
        return new VBox();
    }

    private TilePane createTilePaneContainer() {
        return new TilePane();
    }
}