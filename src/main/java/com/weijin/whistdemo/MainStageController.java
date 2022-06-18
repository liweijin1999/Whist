package com.weijin.whistdemo;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {

    public MainStageController() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param url  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO Auto-generated method stub

    }

    public void changeWindow() throws Exception {
        GamingStage gamingStage =new GamingStage();
        gamingStage.showWindow();


    }
    public void click(ActionEvent event) throws Exception {
        System.out.println("Button Clicked!");
        //todo: 获取选择的难度
        GamingStage gamingStage = new GamingStage();
        gamingStage.showWindow();
    }

}