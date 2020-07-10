package com.azer.meetingmanager.ui.detail;

import java.io.IOException;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.IParent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class MeetingDetailView implements IParent {

    private Parent root;

    public MeetingDetailView(Meeting meeting) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/MeetingDetail.fxml"));
            root = loader.load();
            MeetingDetailController controller = loader.getController();
            controller.notifyDataChanged(meeting);
        } catch (IOException e) {
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

}