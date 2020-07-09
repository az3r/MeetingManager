package com.azer.meetingmanager.ui.master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MasterController implements Initializable {

    @FXML
    private TopbarController topbarController;

    @FXML
    private ListView<Meeting> meetingListView;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meetingListView.setCellFactory(new Callback<ListView<Meeting>, ListCell<Meeting>>() {
            @Override
            public ListCell<Meeting> call(ListView<Meeting> param) {
                return new MeetingItemCell();
            }
        });

        ObservableList<Meeting> values = FXCollections.<Meeting>emptyObservableList();
        meetingListView.setItems(values);
    }

    private static class MeetingItemCell extends ListCell<Meeting> {
        @Override
        public void updateItem(Meeting item, boolean empty) {
            super.updateItem(item, empty);
            try {
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/MeetingItemList.fxml"));
                this.setGraphic(root);
            } catch (IOException e) {
                System.err.println("Unable to load MeetingItemList.fxml");
                throw new ExceptionInInitializerError(e);
            }
        }
    }

}