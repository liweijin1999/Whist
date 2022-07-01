package com.weijin.whistdemo.controllers;

import com.weijin.whistdemo.GamingStage;
import com.weijin.whistdemo.utils.popups;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {

    public ComboBox difficulty;
    public MenuBar menuBar;
    public Button play;
    public Slider difficultySlider;


    public MainStageController() {
        // TODO Auto-generated constructor stub

    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       <tt>null</tt> if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO Auto-generated method stub


    }

    public void changeWindow() throws Exception {
        GamingStage gamingStage = new GamingStage();
        gamingStage.showWindow();


    }

    public void click(ActionEvent event) throws Exception {
        Integer difficultyStr = (int) difficultySlider.getValue();
        GamingStage gamingStage = new GamingStage();
        Stage stage = (Stage) play.getScene().getWindow();
        stage.close();
        gamingStage.showWindow();
    }

    public int getDifficultyLevel() {
        return (int) difficultySlider.getValue();
    }

    public void SliderDrag(DragEvent dragEvent) {
        System.out.println(difficultySlider.getValue());
        sliderReset();
    }

    public void sliderClick(MouseEvent mouseEvent) {
        System.out.println(difficultySlider.getValue());
        sliderReset();
    }

    public void sliderReset() {
        if (difficultySlider.getValue() <= 1.5) {
            difficultySlider.setValue(1);
        } else if (difficultySlider.getValue() < 2.5 && difficultySlider.getValue() > 1.5) {
            difficultySlider.setValue(2);
        } else if (difficultySlider.getValue() >= 2.5) {
            difficultySlider.setValue(3);
        }
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }
}