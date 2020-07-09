package com.azer.meetingmanager.ui.master;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MasterController implements Initializable {


    @FXML 
    private TopbarController topbarController;

    @FXML
    private ListView<Meeting> meetingListView;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}