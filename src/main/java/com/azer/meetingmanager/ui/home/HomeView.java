package com.azer.meetingmanager.ui.home;

import java.io.IOException;

import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class HomeView implements IParent {

    private Parent root;

    public HomeView() {

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/Home.fxml"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public Parent getRoot() {
        return root;
    }

}