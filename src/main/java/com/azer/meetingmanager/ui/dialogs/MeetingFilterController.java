package com.azer.meetingmanager.ui.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.MeetingFilterOption;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class MeetingFilterController extends DialogController<MeetingFilterOption> implements Initializable {

    @FXML
    private ComboBox<String> dateComboBox;

    @FXML
    private TextField startTimeTextField;

    @FXML
    private TextField endTimeTextField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> dateOptions = FXCollections.observableArrayList(MeetingFilterOption.TODAY,
                MeetingFilterOption.WEEK, MeetingFilterOption.MONTH, MeetingFilterOption.YEAR);
        dateComboBox.getItems().addAll(dateOptions);

        startTimeTextField.setText("00:00");
        endTimeTextField.setText("23:59");
    }

    @FXML
    void onCancel(ActionEvent event) {
        setState(STATE_CANCELLED);
        getContainer().close();
    }

    @FXML
    void onFilter(ActionEvent event) {
        MeetingFilterOption result = new MeetingFilterOption();
        
        setState(STATE_COMPLETED);
        setResult(result);

        getContainer().close();
    }
}
