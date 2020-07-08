package com.azer.meetingmanager.ui.home;

import java.io.IOException;
import com.azer.meetingmanager.ui.IParent;

import org.hibernate.SessionFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class HomeView implements IParent {

    private Parent root;

    public HomeView(SessionFactory sessionFactory) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Home.fxml"));
            root = loader.load();
            loader.<HomeController>getController().setSessionFactory(sessionFactory);
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