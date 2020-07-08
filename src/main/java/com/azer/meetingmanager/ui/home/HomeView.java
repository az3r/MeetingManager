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
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Home.fxml"));
            root = loader.load();
            loader.<HomeController>getController().setSessionFactory(App.getSessionFactory());
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