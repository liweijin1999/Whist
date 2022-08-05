package com.weijin;

import com.weijin.controllers.GamingStageController;
import com.weijin.model.Player;
import com.weijin.model.WhistImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.Optional;

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
        String url = "/icons/img.png";
        Image icon = new Image(url);
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1280);//最小宽度
        primaryStage.setMinHeight(880);//最小高度
//        primaryStage.setFullScreen(true);
//        primaryStage.setResizable(false);
        //为当前窗口添加关闭监听
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Exit?");
                alert.setContentText("Are you sure to exit this application?\n\nWarning: All of your progress will be lost.");
                Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                ok.setText("Confirm");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
                //显示对话框
                Optional<ButtonType> result = alert.showAndWait();
                //如果点击OK
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    primaryStage.close();
                    //否则
                } else {
                    event.consume();
                }
            }
        });
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