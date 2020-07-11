package com.azer.meetingmanager.ui;

public interface OnCompleteListener<T> {
    void onCompleted(T result);
    void onError(Exception e);
    void onCancelled();
}