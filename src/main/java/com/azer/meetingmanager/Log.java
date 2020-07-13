package com.azer.meetingmanager;

public class Log {
    public static void i(String TAG, String message) {
        System.out.println(String.format("@%s: %s", TAG, message));
    }

    public static void e(String TAG, String message) {
        System.err.println(String.format("@%s: %s", TAG, message));
    }
}