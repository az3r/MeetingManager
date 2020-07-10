package com.azer.meetingmanager.ui.login;

import java.io.IOException;

import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginDialog implements IParent {

    private Window owner;
    private Parent root;
    private User result;

    public LoginDialog(Window owner) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Login.fxml"));
            root = loader.load();
            this.owner = owner;
        } catch (IOException e) {
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    public void showAndWait() {
        Stage dialog=  new Stage();
        dialog.initOwner(this.owner);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.showAndWait();
    }
    
}