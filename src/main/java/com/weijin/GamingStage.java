package com.weijin;

import com.weijin.controllers.GamingStageController;
import com.weijin.model.Player;
import com.weijin.model.WhistImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class GamingStage extends Application {
    Stage stage = new Stage();
    WhistImpl whist;
    Player you;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/GamingScene.fxml"));
        Parent root = loader.load();
        loader.setRoot(this);
        GamingStageController target = loader.getController();
        target.initController(whist);
        primaryStage.setTitle("Whist (beta1.0)");
        String url="/icons/img.png";
        Image icon= new Image(url);
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
//        primaryStage.setResizable(false);
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