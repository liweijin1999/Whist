package com.weijin.whistdemo;

import com.weijin.whistdemo.controllers.GamingStageController;
import com.weijin.whistdemo.model.WhistImpl;
import com.weijin.whistdemo.controllers.SettleStageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class SettleStage extends Application {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/SettleScene.fxml"));
    Parent root = loader.load();

    public SettleStage() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/SettleScene.fxml")));
        primaryStage.setTitle("WhistDemo (beta1.0)");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public void showScoreBoardWindow(WhistImpl whist, HashMap<String, String> scoreMap, GamingStageController gsc) throws Exception {
        loader.setRoot(this);
        SettleStageController target = loader.getController();
        target.initScoreBoard(whist, scoreMap);
        target.initController(gsc);
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}