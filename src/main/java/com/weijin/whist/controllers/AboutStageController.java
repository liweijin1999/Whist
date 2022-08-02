package com.weijin.whist.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
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
                Developer: Weijin Li
                Description: A project as the Msc. final project at The University of Manchester.
                Version: beta v0.1          
                Repo: https://github.com/liweijin1999/Whist
                               
                                Thank you for playing! Have fun!
                """);
//        Font.loadFont(Objects.requireNonNull(getClass().getResource("../static/fonts/Chalkduster.ttf")).toExternalForm(), 15);
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn2.getScene().getWindow();
        stage.close();
    }
}
