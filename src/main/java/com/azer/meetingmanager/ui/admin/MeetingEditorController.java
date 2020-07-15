package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.ui.BackableController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MeetingEditorController extends BackableController implements Initializable {

    @FXML
    private StackPane photoEmptyPane;

    @FXML
    private ImageView photoImageView;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField dateTextField;

    @FXML
    private TextField monthTextField;

    @FXML
    private TextField yearTextField;

    @FXML
    private TextField hourTextField;

    @FXML
    private TextField minuteTextField;

    @FXML
    private TextField locationNameTextField;

    @FXML
    private TextField locationAddressTextFIeld;

    @FXML
    private TextField locationCapacityTextField;

    @FXML
    private TextArea briefDescriptionTextField;

    @FXML
    private TextArea detailDescriptionTextField;

    @FXML
    void onRestore(ActionEvent event) {

    }

    @FXML
    void onSave(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
