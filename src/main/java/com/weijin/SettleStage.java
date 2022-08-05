package com.weijin;

import com.weijin.controllers.GamingStageController;
import com.weijin.controllers.SettleStageController;
import com.weijin.model.WhistImpl;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

public class SettleStage extends Application {
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout/SettleScene.fxml"));
    Parent root = loader.load();

    public SettleStage() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Whist (beta1.0)");
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/Chalkduster.ttf").toExternalForm()), 15);
        String url="/icons/img.png";
        Image icon= new Image(url);
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
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
            } else {
                event.consume();
            }
        });
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