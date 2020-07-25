package com.azer.meetingmanager.ui;

import com.azer.meetingmanager.ui.dialogs.DialogController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Helper class to load fxml file and display as a Dialog window
 * 
 * @apiNote when using {@link DialogLoader} view's controller must extend
 *          {@link DialogController}
 */
public class DialogLoader<T> {
    private Parent root;
    private DialogController<T> controller;
    private Stage owner;
    private String title = "";

    private DialogLoader(String resource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(resource));
            this.root = loader.load();
            this.controller = loader.getController();
        } catch (Exception e) {
            System.err.println("DialogLoader(" + resource + "): " + e.toString());
            throw new ExceptionInInitializerError(e);
        }
    }

    public DialogLoader(String resource, Stage owner) {
        this(resource);
        this.owner = owner;
    }

    public DialogLoader(String resource, Stage owner, String title) {
        this(resource);
        this.owner = owner;
        this.title = title;
    }

    public Parent getRoot() {
        return root;
    }

    public DialogController<T> getController() {
        return controller;
    }

    public Stage getOwner() {
        return owner;
    }

    public void showAndWait(OnCompleteListener<T> callback) {
        
        Stage container = new Stage();
        container.setTitle(title);
        container.getIcons().setAll(this.owner.getIcons());

        container.setScene(new Scene(getRoot()));
        container.initOwner(getOwner());
        container.initModality(Modality.APPLICATION_MODAL);

        getController().setContainer(container);
        getController().showAndWait(callback);
    }

}