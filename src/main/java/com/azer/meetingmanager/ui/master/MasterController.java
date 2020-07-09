package com.azer.meetingmanager.ui.master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

public class MasterController implements Initializable {

    @FXML
    private TopbarController topbarController;

    @FXML
    private ListView<Meeting> meetingListView;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meetingListView.setCellFactory(param -> new MeetingItemCell());
        meetingListView.setSelectionModel(new NoSelectionModel());
        meetingListView.setFocusTraversable(false);

        ObservableList<Meeting> values = FXCollections.<Meeting>observableArrayList(new Meeting(),new Meeting());
        meetingListView.setItems(values);
    }

    private static class MeetingItemCell extends ListCell<Meeting> {

        @Override
        public void updateItem(Meeting item, boolean empty) {
            super.updateItem(item, empty);

            if (empty && item == null) {
                setText(null);
                setGraphic(null);
            } else if (item != null) {
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

    private static class NoSelectionModel extends MultipleSelectionModel<Meeting> {

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public ObservableList<Meeting> getSelectedItems() {
            return FXCollections.emptyObservableList();
        }

        @Override
        public void selectIndices(int index, int... indices) {
            // TODO Auto-generated method stub

        }

        @Override
        public void selectAll() {
            // TODO Auto-generated method stub

        }

        @Override
        public void selectFirst() {
            // TODO Auto-generated method stub

        }

        @Override
        public void selectLast() {
            // TODO Auto-generated method stub

        }

        @Override
        public void clearAndSelect(int index) {
            // TODO Auto-generated method stub

        }

        @Override
        public void select(int index) {
            // TODO Auto-generated method stub

        }

        @Override
        public void select(Meeting obj) {
            // TODO Auto-generated method stub

        }

        @Override
        public void clearSelection(int index) {
            // TODO Auto-generated method stub

        }

        @Override
        public void clearSelection() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean isSelected(int index) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void selectPrevious() {
            // TODO Auto-generated method stub

        }

        @Override
        public void selectNext() {
            // TODO Auto-generated method stub

        }
    }
}