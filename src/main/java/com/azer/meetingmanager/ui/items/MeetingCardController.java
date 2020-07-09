package com.azer.meetingmanager.ui.items;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.TilePane;

public class MeetingCardController implements Initializable {
    @FXML
    private TilePane meetingTilePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Meeting> values = FXCollections.<Meeting>observableArrayList(new Meeting(), new Meeting(),
                new Meeting(), new Meeting());
        for (Meeting meeting : values) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("views/MeetingItemList.fxml"));
                loadModelData(loader.getController(), meeting);
                Parent root = loader.load();
                meetingTilePane.getChildren().add(root);
            } catch (IOException e) {
                System.err.println("Unable to load MeetingItemCard.fxml");
                throw new ExceptionInInitializerError(e);
            }

        }
    }

    private void loadModelData(MeetingItemController controller, Meeting data) {

    }
}