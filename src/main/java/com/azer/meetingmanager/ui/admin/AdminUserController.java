package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.components.UserItemContainerController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AdminUserController implements Initializable {

    @FXML
    private UserItemContainerController userItemContainerController;

    @FXML
    void onSortByEmailAsc(ActionEvent event) {

    }

    @FXML
    void onSortByNameAsc(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<User> users = App.getUnitOfWork().getUsers();
        userItemContainerController.setItemActionType(true);
        userItemContainerController.notifyCollectionChanged(users);
    }

}
