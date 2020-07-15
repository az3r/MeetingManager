package com.azer.meetingmanager.ui;

import javafx.scene.Parent;

public abstract class BackableController {

    protected Parent upParent;

    public Parent getUpParent() {
        return upParent;
    }

    public void setUpParent(Parent upParent) {
        this.upParent = upParent;
    }
}