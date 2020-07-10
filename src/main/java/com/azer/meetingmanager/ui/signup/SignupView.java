package com.azer.meetingmanager.ui.signup;

import java.io.IOException;
import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class SignupView implements IParent {

    private Parent root;    

    public SignupView() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Signup.fxml"));
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