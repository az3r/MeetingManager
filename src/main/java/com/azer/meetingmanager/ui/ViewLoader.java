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

    /**
     * Preivous root node that navigates to this view
     */
    private Parent previousParent;

    public ViewLoader(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            this.root = loader.load();
            this.controller = loader.getController();
        } catch (Exception e) {
            System.err.println("ViewLoader(" + resource + "): " + e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    public ViewLoader(String resource, Parent previousParent) {
        this(resource);
        this.previousParent = previousParent;
    }

    public ViewLoader(String resource, Stage owner) {
        this(resource);
        this.owner = owner;
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

    public Parent getPreviousParent() {
        return previousParent;
    }
}