package com.azer.meetingmanager.ui.master;

import java.io.IOException;
import com.azer.meetingmanager.ui.IParent;

import org.hibernate.SessionFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MasterView implements IParent {

    private Parent root;

    public MasterView(SessionFactory sessionFactory) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Master.fxml"));
            root = loader.load();
            loader.<MasterController>getController().setSessionFactory(sessionFactory);
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