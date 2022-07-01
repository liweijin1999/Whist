package com.weijin.whistdemo.utils;

import javafx.scene.control.Alert;

public class popups {

    public void ErrorPopup(String errMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error!");
        alert.setContentText(errMessage);
        alert.show();
    }

    public void WarningPopup(String errMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Error!");
        alert.setContentText(errMessage);
        alert.show();
    }

    public static void ConfirmPopup(String errMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Error!");
        alert.setContentText(errMessage);
        alert.show();
    }
}
