package com.azer.meetingmanager.ui.login;

import java.io.IOException;
import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

public class LoginDialog implements IParent {

    private Parent root;

    public LoginDialog(Window owner) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Login.fxml"));
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