package com.azer.meetingmanager;

import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.home.HomeView;
import com.azer.meetingmanager.ui.master.MasterView;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Meetings Manager");
        Scene scene = new Scene(new HomeView().getRoot(), 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        initialize();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static void initialize() throws ExceptionInInitializerError {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
