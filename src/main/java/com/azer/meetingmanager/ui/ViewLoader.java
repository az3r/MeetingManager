package com.azer.meetingmanager.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * Helper class to load fxml file and store its components
 */
public class ViewLoader<T> {

    private Parent root;
    private Stage owner;
    private T controller;

    public ViewLoader(String resource) {
        this(resource, null);
    }
    public ViewLoader(String resource, Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Home.fxml"));
            this.root = loader.load();
            this.controller = loader.getController();
            this.owner = owner;
        } catch (Exception e) {
            System.err.println("ViewLoader(" + resource + "): " + e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    public Parent getRoot() {
        return root;
    }

    public Stage getOwner() {
        return owner;
    }

    public T getController() {
        return controller;
    }
}