package com.weijin.whist;

import com.weijin.whist.controllers.GamingStageController;
import com.weijin.whist.model.WhistImpl;
import com.weijin.whist.controllers.SettleStageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;

public class SettleStage extends Application {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/SettleScene.fxml"));
    Parent root = loader.load();

    public SettleStage() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/SettleScene.fxml")));
        primaryStage.setTitle("Whist (beta0.1)");
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
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