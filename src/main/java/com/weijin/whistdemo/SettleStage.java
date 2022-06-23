package com.weijin.whistdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class SettleStage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/SettleScene.fxml")));
        primaryStage.setTitle("Whist");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void showWindow() throws Exception {
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}