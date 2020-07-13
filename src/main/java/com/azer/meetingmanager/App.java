package com.azer.meetingmanager;

import java.util.List;

import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.UnitOfWork;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.data.repositories.UserRepository;
import com.azer.meetingmanager.data.samples.MeetingSamples;
import com.azer.meetingmanager.data.samples.UserSamples;
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
    private static UnitOfWork unitOfWork = new UnitOfWork();

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
        System.out.println("Closing application...");
        System.out.println("DONE!");
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static UnitOfWork getUnitOfWork() {
        return unitOfWork;
    }

    private static void initialize() throws ExceptionInInitializerError {
        System.out.println("bootstrap hibernate...");
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        System.out.println("DONE!");
    }

    private static void createSamples() {
        int adminCount = 1;
        int userCount = 10;

        System.out.println("Creating database samples...");
        System.out.println("number of users: " + userCount);
        System.out.println("number of admins: " + adminCount);

        System.out.println("initialize UserRepository");
        UserRepository userRepository = new UserRepository(App.getSessionFactory().openSession());

        System.out.println("Creating random admins...");
        List<Admin> admins = UserSamples.createAdmin(adminCount);

        System.out.println("Creating random users...");
        List<User> users = UserSamples.createUser(userCount);

        System.out.println("add all users into database");
        userRepository.insertUser(users);

        System.out.println("add all admins into database");
        userRepository.insertAdmin(admins);

        System.out.println("initialize MeetingRepository");
        MeetingRepository meetingRepository = new MeetingRepository(App.getSessionFactory().openSession());

        System.out.println("creating random meetings...");
        List<Meeting> meetings = MeetingSamples.createMeeting(10);

        System.out.println("add all meetings into database");
        meetingRepository.insert(meetings);

        System.out.println("create a static user to use each launch");
        userRepository.insert(UserSamples.createUser("user", "123"));

        System.out.println("create a static admin to use each launch");
        userRepository.insertAdmin(UserSamples.createAdmin("admin", "123"));

        System.out.println("Closing all repositories...");
        userRepository.close();
        meetingRepository.close();
        System.out.println("DONE!");
    }
}
