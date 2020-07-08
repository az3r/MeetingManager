package com.azer.meetingmanager.ui.home;

import java.io.IOException;

import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HomeView implements IParent {

    private Parent root;

    public HomeView() {

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/Home.fxml"));
            Image image = new Image(getClass().getClassLoader().getResource("icons/background.jpg").toExternalForm());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

}