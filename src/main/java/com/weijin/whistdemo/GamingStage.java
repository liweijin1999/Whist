package com.weijin.whistdemo;

import com.weijin.whistdemo.model.Player;
import com.weijin.whistdemo.model.WhistImpl;
import com.weijin.whistdemo.controllers.GamingStageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GamingStage extends Application {
    Stage stage = new Stage();
    WhistImpl whist;
    Player you;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/GamingScene.fxml"));
        Parent root = loader.load();
        loader.setRoot(this);
        GamingStageController target = loader.getController();
        target.initController(whist);
        primaryStage.setTitle("WhistDemo (beta1.0)");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void mainToShowWindow(Integer difficultyInt) throws Exception {
        whist = new WhistImpl();
        you = new Player();
        you.setId("player1 (you)");
        whist.loadPlayers(you);
        whist.setDifficulty(difficultyInt);
        start(stage);
    }

    public void SettlementToShowWindow(WhistImpl whistImpl) throws Exception {
        whist = whistImpl;
        start(stage);
    }

}