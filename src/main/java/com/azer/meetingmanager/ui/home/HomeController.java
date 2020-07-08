package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;

import org.hibernate.SessionFactory;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML
    private StackPane overlayContainer;
    @FXML
    private BorderPane overlay;

    private SessionFactory sessionFactory;
    private UserRepository userRepository;
    private MeetingRepository meetingRepository;


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onOverlayFadeIn(MouseEvent event) {
        overlay.setVisible(true);
        FadeTransition fadein = new FadeTransition(new Duration(100), overlay);
        fadein.setInterpolator(Interpolator.EASE_OUT);
        fadein.setFromValue(0);
        fadein.setToValue(0.8);
        fadein.play();
    }

    @FXML
    void onOverlayFadeOut(MouseEvent event) {
        FadeTransition fadeout = new FadeTransition(new Duration(200), overlay);
        fadeout.setInterpolator(Interpolator.EASE_OUT);
        fadeout.setOnFinished(e -> overlay.setVisible(false));
        fadeout.setFromValue(0.8);
        fadeout.setToValue(0);
        fadeout.play();
    }
}