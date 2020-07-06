package com.azer.meetingmanager;

import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Meetings Manager");


        primaryStage.setScene(new MainScene(new BorderPane(), 1440, 1024));

        primaryStage.show();
    }
}
