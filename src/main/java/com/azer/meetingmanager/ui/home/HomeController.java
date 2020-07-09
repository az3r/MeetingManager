package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.ui.overlay.OverlayController;
import com.azer.meetingmanager.ui.topbar.TopbarController;

import org.hibernate.SessionFactory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class HomeController implements Initializable {

    @FXML
    private TopbarController topbarController;
    @FXML
    private OverlayController overlayController;

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

}