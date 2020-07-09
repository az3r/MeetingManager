package com.azer.meetingmanager.ui.overlay;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class OverlayController {

    @FXML
    private HBox overlay;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    public void setLeftButtonText(String value) {
        leftButton.setText(value);
    }

    public void setRightButtonText(String value) {
        rightButton.setText(value);
    }

    public void setLeftButtonOnAction(EventHandler<ActionEvent> handler) {
        leftButton.setOnAction(handler);
    }

    public void setRightButtonOnAction(EventHandler<ActionEvent> handler) {
        rightButton.setOnAction(handler);
    }
}
