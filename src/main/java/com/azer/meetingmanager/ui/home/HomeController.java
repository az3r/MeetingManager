package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.components.OverlayController;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.master.MasterController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable {

    @FXML
    private TopbarController topbarController;
    @FXML
    private OverlayController overlayController;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTopbar();
        setupOverlay();

    }

    private void setupTopbar() {
        topbarController.setTitle("Home");
        topbarController.showBackButton(false);
    }

    private void setupOverlay() {
        overlayController.setLeftButtonText("more");
        overlayController.setLeftButtonOnAction(e -> {
            ViewLoader<MasterController> loader = new ViewLoader<>("views/Master.fxml");
            root.getScene().setRoot(loader.getRoot());

        });
        overlayController.setRightButtonText("detail");
    }

}