package com.azer.meetingmanager.ui.admin;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.helpers.Utility;
import com.azer.meetingmanager.ui.BackableController;
import com.azer.meetingmanager.ui.OnItemActionListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private Button leftButton;

    @FXML
    private Button rightButton;

    private Meeting meeting;

    public void setLeftButtonText(String value) {
        leftButton.setText(value.toUpperCase());
    }

    public void setRightButtonText(String value) {
        rightButton.setText(value.toUpperCase());
    }

    public void setLeftButtonAction(OnItemActionListener<Meeting> listener) {
        leftButton.setOnAction(e -> listener.onAction(meeting));
    }

    public void setRightButtonAction(OnItemActionListener<Meeting> listener) {
        rightButton.setOnAction(e -> listener.onAction(meeting));
    }

    public void notifyDataChanged(Meeting meeting) {
        this.meeting = meeting;

        titleTextField.setText(meeting.getName());
        briefDescriptionTextField.setText(meeting.getShortDesc());
        detailDescriptionTextField.setText(meeting.getDetailDesc());

        locationNameTextField.setText(meeting.getLocation().getName());
        locationAddressTextFIeld.setText(meeting.getLocation().getAddress());
        locationCapacityTextField.setText(String.valueOf(meeting.getLocation().getCapacity()));

        Calendar calender = Calendar.getInstance();
        calender.setTime(meeting.getHoldTime());
        
        dateTextField.setText(String.valueOf(calender.get(Calendar.DAY_OF_MONTH))); 
        monthTextField.setText(String.valueOf(calender.get(Calendar.MONTH) + 1)); 
        yearTextField.setText(String.valueOf(calender.get(Calendar.YEAR))); 
        hourTextField.setText(String.valueOf(calender.get(Calendar.HOUR_OF_DAY))); 
        minuteTextField.setText(String.valueOf(calender.get(Calendar.MINUTE)));

        photoImageView.setImage(Utility.getImage(meeting.getPhoto()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
