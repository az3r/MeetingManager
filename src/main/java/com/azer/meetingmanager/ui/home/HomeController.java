package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.ui.master.MasterView;
import com.azer.meetingmanager.ui.overlay.OverlayController;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import org.hibernate.SessionFactory;

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
        topbarController.useLoggedUserTopbar(false);
    }

    private void setupOverlay() {
        overlayController.setLeftButtonText("more");
        overlayController.setLeftButtonOnAction(e -> {
            MasterView view = new MasterView();
            root.getScene().setRoot(view.getRoot());

        });
        overlayController.setRightButtonText("detail");
    }

}