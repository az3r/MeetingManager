package com.azer.meetingmanager.data.samples;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.azer.meetingmanager.data.models.Account;
import com.azer.meetingmanager.data.models.Admin;
import com.azer.meetingmanager.data.models.User;

public class UserSamples {
    private static Random generator = new Random();
    private static SecureRandom secureGenerator = new SecureRandom();
    private static MessageDigest digester = null;

    private static List<String> names = Arrays.asList("Liam Emma", "Noah Olivia", "James Isabella", "Oliver Sophia",
            "Elijah Mia", "Lucas Amelia", "Mason Harper", "Logan Evelyn", "William Ava", "Benjamin Charlotte");

    private static List<String> passwords = Arrays.asList("password", "password", "123456", "123456", "123456",
            "123456", "123456", "123456", "123456", "123456", "password", "password", "password", "password",
            "password", "password", "123456789", "12345678", "12345678", "12345678", "12345", "12345678", "12345",
            "12345678", "123456789", "qwerty", "qazwsx", "ninja", "azerty", "123123", "solo", "loveme", "whatever",
            "donald", "dragon", "michael", "mustang", "trustno1", "batman", "passw0rd", "zaq1zaq1", "qazwsx",
            "password1", "password1", "Football", "password1", "000000", "trustno1", "starwars", "password1",
            "trustno1", "qwerty123", "123qwe");

    public static Admin createAdmin() {
        int x = generator.nextInt(names.size());
        int y = generator.nextInt(passwords.size());

        String fullname = names.get(x);
        String password = passwords.get(y);

        String accountName = getAccountName(fullname);
        String email = getEmail(accountName);
        byte[] salt = getSalt();
        byte[] hashedPassword = hashPassword(salt, password);

        Account account = new Account(accountName, salt, hashedPassword);
        Admin admin = new Admin(fullname, email, account);

        return admin;

    }

    public static User createUser() {
        int x = generator.nextInt(names.size());
        int y = generator.nextInt(passwords.size());

        String fullname = names.get(x);
        String password = passwords.get(y);

        String accountName = getAccountName(fullname);
        String email = getEmail(accountName);
        byte[] salt = getSalt();
        byte[] hashedPassword = hashPassword(salt, password);

        Account account = new Account(accountName, salt, hashedPassword);
        User user = new User(fullname, email, account);

        return user;
    }

    public static List<User> createUser(int size) {
        ArrayList<User> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(createUser());
        }
        return list;
    }

    public static List<Admin> createAdmin(int size) {
        ArrayList<Admin> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(createAdmin());
        }
        return list;
    }

    private static String getEmail(String value) {
        return value + "@gmail.com";
    }

    private static byte[] getSalt() {
        byte[] salt = new byte[16];
        secureGenerator.nextBytes(salt);
        return salt;
    }

    private static byte[] hashPassword(byte[] salt, String password) {
        if (digester == null) {
            try {
                digester = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        digester.update(salt);
        return digester.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private static String getAccountName(String fullname) {
        return getAccountName(fullname, false);
    }

    private static String getAccountName(String fullname, boolean admin) {
        String firstName = fullname.split(" ")[0];
        return admin ? "admin-" : "member-" + firstName;
    }
}
