package com.weijin.whistdemo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutStageController implements Initializable {
    public Button okBtn2;
    public Label aboutLabel;

    public AboutStageController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aboutLabel.setText("""
                Name of Game: Whist
                Developer: Weijin
                Description: A project as the Msc. final project at University of Manchester.
                Version: beta v0.1
                                
                            Thank you for playing! Have fun!
                """);
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn2.getScene().getWindow();
        stage.close();
    }
}
