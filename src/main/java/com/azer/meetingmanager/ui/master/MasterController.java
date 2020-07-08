package com.azer.meetingmanager.ui.master;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import org.hibernate.SessionFactory;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class MasterController implements Initializable {


    @FXML Button switchButton;
    @FXML TopbarController topbarController;

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
    public void onSwitchTopbar(ActionEvent event) {
        topbarController.showBackButton(false);
    }
}