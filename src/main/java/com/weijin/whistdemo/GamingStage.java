package com.weijin.whistdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class GamingStage extends Application {
    Stage stage=new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/GamingScene.fxml")));
        primaryStage.setTitle("Whist");
        primaryStage.setScene(new Scene(root));
//        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() throws Exception {
        start(stage);
    }

}