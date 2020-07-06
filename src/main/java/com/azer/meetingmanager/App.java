package com.azer.meetingmanager;

// import javafx.application.*;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;

import com.azer.meetingmanager.data.models.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.mariadb.jdbc.Driver;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App  extends Application {
    public static void main(String[] args) {

        SessionFactory factory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {

            factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        } finally {
            if (factory != null) {
                factory.close();
            }
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Meetings Manager");

        primaryStage.setScene(new MainScene(new BorderPane(), 1440, 1024));

        primaryStage.show();
    }
}
