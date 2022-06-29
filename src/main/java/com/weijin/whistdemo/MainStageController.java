package com.weijin.whistdemo;

import com.weijin.whistdemo.utils.popups;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
//        System.out.println("Button Clicked!");
        //todo: 获取选择的难度
        String difficultyStr = getDifficultyLevel();
//        System.out.println(difficultyStr);
        if (difficultyStr != null) {
            GamingStage gamingStage = new GamingStage();
            gamingStage.showWindow();
        }
    }

    public String getDifficultyLevel() throws Exception {
        try {
            return difficulty.getValue().toString();
        } catch (Exception e) {
            popups popups = new popups();
            popups.ErrorPopup("Please select a difficulty level");
        }
        return null;
    }
}