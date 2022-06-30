package com.weijin.whistdemo;

import com.weijin.whistdemo.component.Player;
import com.weijin.whistdemo.component.WhistImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class GamingStage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout/GamingScene.fxml"));
        Parent root = loader.load();
        loader.setRoot(this);
        GamingStageController target = loader.getController();
        WhistImpl whist = new WhistImpl();
        Player you = new Player();
        you.setId("player1 (you)");
        whist.loadPlayers(you);
        target.initController(whist);
        primaryStage.setTitle("Whist");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
//        you.psc.addPropertyChangeListener("setTurn_pro", new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println(evt.getOldValue());
//                System.out.println(evt.getNewValue());
////                System.out.println(evt.getSource().getClass());
//            }
//        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showWindow() throws Exception {
        start(stage);
    }

}