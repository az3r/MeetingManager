package com.azer.meetingmanager.ui.master;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.topbar.TopbarController;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MasterController implements Initializable {

    private static final String DISPLAY_LIST = "List view";
    private static final String DISPLAY_CARD = "Card view";

    @FXML
    private TopbarController topbarController;

    @FXML
    private VBox displayLayout;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        useListView();
    }

    private void setupTopbar() {
        topbarController.setTitle("Meetings");
        topbarController.useLoggedUserTopbar(false);
    }

    private void useListView() {
        displayButton.setText(DISPLAY_LIST);

        ObservableList<Node> children = displayLayout.getChildren();

        // remove GridPane if exists
        if (children.get(children.size() - 1) instanceof GridPane) {
            children.remove(children.size() - 1);

        }

        // add ListView
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/MeetingListView.fxml"));
            children.add(root);
        } catch (IOException e) {
            System.err.println("Unable to load MeetingListView.faml");
            throw new ExceptionInInitializerError(e);
        }
    }

    private void useCardView() {
        displayButton.setText(DISPLAY_CARD);

        ObservableList<Node> children = displayLayout.getChildren();

        // remove ListView if exists
        if (children.get(children.size() - 1) instanceof ListView) {
            children.remove(children.size() - 1);

        }

        // add GridPane
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/MeetingCardView.fxml"));
            children.add(root);
        } catch (IOException e) {
            System.err.println("Unable to load MeetingCardView.faml");
            throw new ExceptionInInitializerError(e);
        }
    }

    @FXML
    public void onChangeDisplay(ActionEvent event) {
        if (displayButton.getText().equals(DISPLAY_LIST)) {
            displayButton.setText(DISPLAY_CARD);
            useCardView();

        } else {

            displayButton.setText(DISPLAY_LIST);
            useListView();
        }
    }
}