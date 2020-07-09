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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

public class MeetingListController implements Initializable {
    @FXML
    private ListView<Meeting> meetingListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meetingListView.setCellFactory(param -> new MeetingItemCell());
        meetingListView.setSelectionModel(new NoSelectionModel());
        meetingListView.setFocusTraversable(false);

        ObservableList<Meeting> values = FXCollections.<Meeting>observableArrayList(new Meeting(), new Meeting(),
                new Meeting(), new Meeting());
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
                FXMLLoader loader = new FXMLLoader(
                        getClass().getClassLoader().getResource("views/MeetingItemList.fxml"));
                Parent root = loader.load();
                loadModelData(loader.getController(), item);
                this.setGraphic(root);
            } catch (IOException e) {
                System.err.println("Unable to load MeetingItemList.fxml");
                throw new ExceptionInInitializerError(e);
            }
        }
    }

    private void loadModelData(MeetingItemController controller, Meeting data) {

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
    
        }
    
        @Override
        public void selectAll() {
    
        }
    
        @Override
        public void selectFirst() {
    
        }
    
        @Override
        public void selectLast() {
    
        }
    
        @Override
        public void clearAndSelect(int index) {
    
        }
    
        @Override
        public void select(int index) {
    
        }
    
        @Override
        public void select(Meeting obj) {
    
        }
    
        @Override
        public void clearSelection(int index) {
    
        }
    
        @Override
        public void clearSelection() {
    
        }
    
        @Override
        public boolean isSelected(int index) {
            return false;
        }
    
        @Override
        public boolean isEmpty() {
            return false;
        }
    
        @Override
        public void selectPrevious() {
    
        }
    
        @Override
        public void selectNext() {
    
        }
    }
    

    
}