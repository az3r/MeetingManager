package com.azer.meetingmanager;

import java.util.List;

// import javafx.application.*;
// import javafx.scene.layout.BorderPane;
// import javafx.stage.Stage;

import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.ui.MainScene;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App  extends Application {
    public static void main(String[] args) {

        SessionFactory factory = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        try {

            factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            UserRepository repository = new UserRepository(factory);
            List<User> users = repository.getAll();
            System.out.println(users.get(0).toString());

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
