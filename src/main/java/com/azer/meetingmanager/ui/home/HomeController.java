package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;

import org.hibernate.SessionFactory;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class HomeController implements Initializable {

    @FXML private StackPane overlayContainer;
    @FXML private BorderPane overlay;

    private SessionFactory sessionFactory;
    private UserRepository userRepository;
    private MeetingRepository meetingRepository;

    public HomeController(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            throw new NullPointerException("sessionFactory is null");
        }
        this.sessionFactory = sessionFactory;
        userRepository = new UserRepository(sessionFactory);
        meetingRepository = new MeetingRepository(sessionFactory);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void onOverlayFadeIn(MouseEvent event) {
        FadeTransition fadein = new FadeTransition(new Duration(0.3), overlay);
        fadein.setFromValue(0);
        fadein.setToValue(0.8);

    }
}