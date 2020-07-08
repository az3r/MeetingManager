package com.azer.meetingmanager.ui.topbar;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TopbarController implements Initializable {

    @FXML
    private HBox homeLayout;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchView;

    @FXML Text titleText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {    
        backButton.managedProperty().bind(backButton.visibleProperty());
    }

    public void showBackButton(boolean visible){
        backButton.setVisible(visible);
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }
    
}