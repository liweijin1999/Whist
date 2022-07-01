package com.weijin.whistdemo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutStageController implements Initializable {
    public Button okBtn2;

    public AboutStageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn2.getScene().getWindow();
        stage.close();
    }
}
