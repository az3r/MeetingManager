package com.azer.meetingmanager;

import java.util.List;

import com.azer.meetingmanager.data.UnitOfWork;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.data.samples.MeetingSamples;
import com.azer.meetingmanager.data.samples.UserSamples;
import com.azer.meetingmanager.helpers.Utility;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.home.HomeController;

import javafx.scene.image.Image;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static final String TAG = "App";
    private static SessionFactory sessionFactory;
    private static Application app = null;
    private static final UnitOfWork unitOfWork = new UnitOfWork();

    public static void main(String[] args) {

        launch(args);
    }

    public static Application getInstance() {
        return app;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        app = this;
        primaryStage.setTitle("Meetings Manager");
        primaryStage.getIcons().add(new Image(App.class.getClassLoader().getResourceAsStream("icons/app-2.png")));

        ViewLoader<HomeController> loader = new ViewLoader<>("views/Home.fxml");
        Scene scene = new Scene(loader.getRoot(), 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        initialize();
        createSamples();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Log.i(TAG, "Closing application...");
        sessionFactory.close();
        Log.i(TAG, "Closed Session Factory");
        Log.i(TAG, "DONE!");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    private static void initialize() throws ExceptionInInitializerError {
        Log.i(TAG, "bootstrap hibernate...");
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Log.i(TAG, "DONE!");
    }

    private static void createSamples() {
        int userCount = 10;

        Log.i(TAG, "Creating database samples...");
        Log.i(TAG, "number of users: " + userCount);

        UserRepository userRepository = new UserRepository(App.getSessionFactory().openSession());
        MeetingRepository meetingRepository = new MeetingRepository(App.getSessionFactory().openSession());

        Log.i(TAG, "Creating random users...");
        List<User> users = UserSamples.createUser(userCount);

        Log.i(TAG, "add all users into database");
        userRepository.insertUser(users);

        Log.i(TAG, "creating random meetings...");
        List<Meeting> meetings = MeetingSamples.createMeeting(10);

        Log.i(TAG, "add all meetings into database");
        meetingRepository.insert(meetings);

        User staticUser = UserSamples.createUser("tuan nguyen", "tuan@gmail.com", "user", false, "123");
        userRepository.insert(staticUser);
        Log.i(TAG, "created a static " + staticUser);

        Admin staticAdmin = UserSamples.createAdmin("tuan nguyen", "tuan@gmail.com", "admin", false, "123");
        Log.i(TAG, "create a static " + staticAdmin);
        userRepository.insertAdmin(staticAdmin);

        Log.i(TAG, "commit all changed");
        userRepository.commit();
        meetingRepository.commit();

        Log.i(TAG, "Closing all repositories...");
        userRepository.close();
        meetingRepository.close();
        Log.i(TAG, "DONE!");
    }
}
