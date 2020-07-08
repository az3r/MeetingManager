package com.azer.meetingmanager.ui.home;

import java.io.IOException;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomeView implements IParent {

    private Parent root;

    public HomeView() {

        try {
            FXMLLoader loader = FXMLLoader.load(getClass().getClassLoader().getResource("views/Home.fxml"));
            loader.<HomeController>getController().setSessionFactory(App.getSessionFactory());
            root = loader.load();
        } catch (IOException e) {
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

}