package com.weijin.whistdemo.utils;

import javafx.scene.control.Alert;

public class popups {

    public void ErrorPopup(String errMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error!");
        alert.setContentText(errMessage);
        alert.show();
    }
}
