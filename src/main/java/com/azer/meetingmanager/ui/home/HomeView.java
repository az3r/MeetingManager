package com.azer.meetingmanager.ui.home;

import java.io.IOException;

import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomeView implements IParent {

    private Parent root;

    public HomeView() {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/Home.fxml"));
        } catch (IOException e) {
            System.err.println("Unable to load fxml resource: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

}