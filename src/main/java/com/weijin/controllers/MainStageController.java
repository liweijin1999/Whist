package com.weijin.controllers;

import com.weijin.stages.AboutStage;
import com.weijin.stages.GamingStage;
import com.weijin.stages.RuleStage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {
    @FXML
    public MenuBar menuBar;
    @FXML
    public Button playBtn;
    @FXML
    public Slider difficultySlider;
    public ImageView iconImg;


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
        iconImg.setImage(new Image("/static/pictures/start.png"));
    }

    public void click(ActionEvent event) throws Exception {
        Integer difficultyInt = (int) difficultySlider.getValue();
        GamingStage gamingStage = new GamingStage();
        Stage stage = (Stage) playBtn.getScene().getWindow();
        stage.close();
        gamingStage.mainToShowWindow(difficultyInt);
    }

    public void SliderDrag(DragEvent dragEvent) {
//        System.out.println(difficultySlider.getValue());
        sliderReset();
    }

    public void sliderClick(MouseEvent mouseEvent) {
//        System.out.println(difficultySlider.getValue());
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

    public void showRules(ActionEvent actionEvent) throws Exception {
        RuleStage ruleStage = new RuleStage();
        ruleStage.showWindow();
    }

    public void showAbout(ActionEvent actionEvent) throws Exception {
        AboutStage aboutStage = new AboutStage();
        aboutStage.showWindow();
    }
}