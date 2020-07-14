package com.azer.meetingmanager.helpers;

public class StringHelper {
    public static boolean nullOrEmpty(String value) {
        return value != null ? value.isEmpty() : true;
    }
}