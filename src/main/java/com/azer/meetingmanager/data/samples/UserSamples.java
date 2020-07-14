package com.azer.meetingmanager.data.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.azer.meetingmanager.data.models.Account;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.helpers.AccountHelper;

public class UserSamples {
    private static Random generator = new Random();

    private static List<String> names = Arrays.asList("Liam Emma", "Noah Olivia", "James Isabella", "Oliver Sophia",
            "Elijah Mia", "Lucas Amelia", "Mason Harper", "Logan Evelyn", "William Ava", "Benjamin Charlotte");

    private static List<String> passwords = Arrays.asList("password", "password", "123456", "123456", "123456",
            "123456", "123456", "123456", "123456", "123456", "password", "password", "password", "password",
            "password", "password", "123456789", "12345678", "12345678", "12345678", "12345", "12345678", "12345",
            "12345678", "123456789", "qwerty", "qazwsx", "ninja", "azerty", "123123", "solo", "loveme", "whatever",
            "donald", "dragon", "michael", "mustang", "trustno1", "batman", "passw0rd", "zaq1zaq1", "qazwsx",
            "password1", "password1", "Football", "password1", "000000", "trustno1", "starwars", "password1",
            "trustno1", "qwerty123", "123qwe");

    public static User createAccount(String userName, String email, String accountName, String password, boolean admin) {
        byte[] salt = AccountHelper.generateSalt(16);
        byte[] hashedPassword = AccountHelper.generatePassword(password, salt);
        Account account = new Account(accountName, salt, hashedPassword);
        User user = admin ? new Admin(userName, email, account) : new User(userName, email, account) ;
        return user;
    }

    public static Admin createAdmin(String userName, String email, String accountName, String password) {
        return (Admin) createAccount(userName, email, accountName, password, true);
    }

    public static User createUser(String userName, String email, String accountName, String password) {
        return createAccount(userName, email, accountName, password, false);
    }

    public static User createRandomAccount(boolean admin) {
        int x = generator.nextInt(names.size());
        int y = generator.nextInt(passwords.size());

        String fullname = names.get(x);
        String password = passwords.get(y);

        String accountName = getAccountName(fullname, admin);
        String email = getEmail(accountName);
        byte[] salt = AccountHelper.generateSalt(16);
        byte[] hashedPassword = AccountHelper.generatePassword(password, salt);

        Account account = new Account(accountName, salt, hashedPassword);
        return new Admin(fullname, email, account);
    }

    public static Admin createRandomAdmin() {
        return (Admin) createRandomAccount(true);
    }

    public static User createRandomUser() {
        return createRandomAccount(false);
    }

    public static List<User> createUser(int size) {
        ArrayList<User> list = new ArrayList<>();
        List<Integer> smallest = Arrays.asList(size,names.size(),passwords.size());
        smallest.sort(Integer::compare);

        for (int i = 0; i < smallest.get(0); i++) {
            String userName = names.get(i);
            String password = passwords.get(i);
            String accountName = getAccountName(userName, false);
            String email = getEmail(accountName);
            list.add(createUser(userName, email, accountName, password));
        }
        return list;
    }

    private static String getEmail(String value) {
        return value + "@gmail.com";
    }

    private static String getAccountName(String fullname, boolean admin) {
        String firstName = fullname.split(" ")[0];
        return admin ? "admin-" + firstName : "member-" + firstName;
    }
}
