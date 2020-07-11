package com.azer.meetingmanager.ui.components;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class OverlayController {

    @FXML
    private HBox overlay;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    public void setLeftButtonText(String value) {
        leftButton.setText(value.toUpperCase());
    }

    public void setRightButtonText(String value) {
        rightButton.setText(value.toUpperCase());
    }

    public void setLeftButtonAction(EventHandler<ActionEvent> handler) {
        leftButton.setOnAction(handler);
    }

    public void setRightButtonAction(EventHandler<ActionEvent> handler) {
        rightButton.setOnAction(handler);
    }

    @FXML
    void onOverlayFadeIn(MouseEvent event) {
        FadeTransition fadein = new FadeTransition(new Duration(100), overlay);
        fadein.setInterpolator(Interpolator.EASE_OUT);
        fadein.setFromValue(0);
        fadein.setToValue(0.9);
        fadein.play();
    }

    @FXML
    void onOverlayFadeOut(MouseEvent event) {
        FadeTransition fadeout = new FadeTransition(new Duration(200), overlay);
        fadeout.setInterpolator(Interpolator.EASE_OUT);
        fadeout.setFromValue(0.9);
        fadeout.setToValue(0);
        fadeout.play();
    }
}
