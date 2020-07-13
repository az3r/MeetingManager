package com.azer.meetingmanager.helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AccountHelper {
    public static byte[] generateSalt(int size) {
        SecureRandom secureGenerator = new SecureRandom();
        byte[] salt = new byte[16];
        secureGenerator.nextBytes(salt);
        return salt;
    }

    public static byte[] generatePassword(String password, byte[] salt) {
        MessageDigest digester = null;
        try {
            digester = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] hashedPassword = null;
        if (digester != null) {
            digester.update(salt);
            hashedPassword = digester.digest(password.getBytes());
        }
        return hashedPassword;
    }
}