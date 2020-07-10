package com.azer.meetingmanager.ui;

public interface OnCompleteListener<T> {
    void onCompleted(T result);
    void onCancelled(String reason);
}