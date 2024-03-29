package com.weijin.stages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Objects;

public class RuleStage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/RulePage.fxml"));
        Font.loadFont(Objects.requireNonNull(getClass().getResource("/fonts/BRADHITC.TTF").toExternalForm()), 15);
        primaryStage.setTitle("Whist Rules");
        String url="/icons/img.png";
        Image icon= new Image(url);
        primaryStage.getIcons().add(icon);
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);
//        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void showWindow() throws Exception {
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}