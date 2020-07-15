package com.azer.meetingmanager.ui.dialogs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.azer.meetingmanager.Log;
import com.azer.meetingmanager.data.models.Location;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.helpers.StringHelper;
import com.azer.meetingmanager.helpers.Utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MeetingEditorController extends DialogController<Meeting> implements Initializable {

    private static final String TAG = "MeetingEditorController";

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

    private File imageFile;

    private Meeting meeting;

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

        if(meeting.getPhoto() != null)
            photoImageView.setImage(Utility.getImage(meeting.getPhoto()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onSelectImageFromFile(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        ExtensionFilter filter = new ExtensionFilter("Image files", "*.png", "*.jpg");
        chooser.getExtensionFilters().add(filter);

        File file = chooser.showOpenDialog(dateTextField.getScene().getWindow());
        if (file != null && file.exists()) {
            try {
                byte[] photoData = Files.readAllBytes(file.toPath());
                photoImageView.setImage(Utility.getImage(photoData));
                imageFile = file;
            } catch (IOException ioe) {
                Log.e(TAG, ioe.toString());
            }
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        if (checkNullFields()) {
            new Alert(AlertType.WARNING, "All fields must not be empty", ButtonType.OK).showAndWait();
            return;
        }

        if (checkTimeFields()) {
            new Alert(AlertType.WARNING, "Time fields must be numbers only", ButtonType.OK).showAndWait();
            return;
        }

        meeting.setName(titleTextField.getText());
        meeting.setShortDesc(briefDescriptionTextField.getText());
        meeting.setDetailDesc(detailDescriptionTextField.getText());

        Location location = new Location();
        location.setAddress(locationAddressTextFIeld.getText());
        location.setName(locationNameTextField.getText());
        location.setCapacity(Integer.parseInt(locationCapacityTextField.getText()));
        meeting.setLocation(location);

        GregorianCalendar calender = new GregorianCalendar(Integer.parseInt(yearTextField.getText()),
                Integer.parseInt(monthTextField.getText()) - 1, Integer.parseInt(dateTextField.getText()),
                Integer.parseInt(hourTextField.getText()), Integer.parseInt(minuteTextField.getText()));

        meeting.setHoldTime(calender.getTime());

        try {
            if (imageFile != null) {
                meeting.setPhoto(Files.readAllBytes(imageFile.toPath()));
                photoImageView.setImage(Utility.getImage(meeting.getPhoto()));
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

        success(meeting);
        getContainer().close();
    }

    @FXML
    private void onCancel(ActionEvent e) {
        cancelled();
        getContainer().close();
    }

    private boolean checkNullFields() {
        boolean nullOrEmpty = false;
        if (StringHelper.nullOrEmpty(titleTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(briefDescriptionTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(detailDescriptionTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(locationNameTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(locationAddressTextFIeld.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(locationCapacityTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(dateTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(monthTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(yearTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(hourTextField.getText()))
            nullOrEmpty = true;
        else if (StringHelper.nullOrEmpty(minuteTextField.getText()))
            nullOrEmpty = true;
        return nullOrEmpty;
    }

    private boolean checkTimeFields() {
        try {
            Integer.parseInt(dateTextField.getText());
            Integer.parseInt(monthTextField.getText());
            Integer.parseInt(yearTextField.getText());
            Integer.parseInt(hourTextField.getText());
            Integer.parseInt(minuteTextField.getText());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
