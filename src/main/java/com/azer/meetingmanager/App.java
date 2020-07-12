package com.azer.meetingmanager;

import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.home.HomeController;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static SessionFactory sessionFactory;
    private static Application app = null;

    public static void main(String[] args) {

        initialize();
        // launch(args);
    }

    public static Application getInstance(){
        return app;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        app = this;

        primaryStage.setTitle("Meetings Manager");
        ViewLoader<HomeController> loader = new ViewLoader<>("views/Home.fxml");
        Scene scene = new Scene(loader.getRoot(), 1200, 800);
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
