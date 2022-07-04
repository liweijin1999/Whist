package com.weijin.whistdemo;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.Objects;

public class MainStage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("layout/MainScene.fxml")));
        primaryStage.setTitle("WhistDemo (beta0.1)");
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("static/css/common.css").toExternalForm();
        scene.getStylesheets().add(css);

        //test
//        Circle target = new Circle(100, 100, 50);
//        Rectangle rectangle = new Rectangle(100, 100, Color.RED);
//        KeyValue kv1x = new KeyValue(rectangle.translateXProperty(), 0);
//        KeyValue kv1y = new KeyValue(rectangle.translateYProperty(),0);
//        KeyValue kv1sx = new KeyValue(rectangle.scaleXProperty(), 1);
//        KeyValue kv1sy = new KeyValue(rectangle.scaleYProperty(), 1);
//        KeyFrame kf1=new KeyFrame(Duration.seconds(0),kv1x,kv1y,kv1sx,kv1sy);
//
//        KeyValue kv2x = new KeyValue(rectangle.translateXProperty(), 500);
//        KeyValue kv2y = new KeyValue(rectangle.translateYProperty(), target.centerYProperty().get());
//        KeyValue kv2sx = new KeyValue(rectangle.scaleXProperty(), 0.3);
//        KeyValue kv2sy = new KeyValue(rectangle.scaleYProperty(), 0.3);
//        KeyFrame kf2=new KeyFrame(Duration.seconds(0.6),kv2x,kv2y,kv2sx,kv2sy);
//
//        Timeline timeline = new Timeline();
//        timeline.getKeyFrames().addAll(kf1,kf2);
//
//        timeline.play();
//        AnchorPane root1 = new AnchorPane();
//        root1.getChildren().addAll(rectangle,target);
//        AnchorPane.setLeftAnchor(rectangle, 400.0);
//        AnchorPane.setTopAnchor(rectangle, 200.0);
//        AnchorPane.setLeftAnchor(target, 900.0);
//        AnchorPane.setTopAnchor(target, 600.0);
//        System.out.println(rectangle.localToScene(rectangle.getLayoutBounds()).getMinX()+"   "+rectangle.layoutYProperty().get());
//        System.out.println(target.centerXProperty().get()+"   "+target.centerYProperty().get());
//        Scene scene=new Scene(root1);
//        primaryStage.setWidth(1500);
//        primaryStage.setHeight(1000);
//        primaryStage.centerOnScreen();
        //test
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void showWindow() throws Exception {
        start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}