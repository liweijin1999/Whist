package com.weijin.whistdemo.controllers;

import com.weijin.whistdemo.model.SettleScoreBoard;
import com.weijin.whistdemo.model.WhistImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SettleStageController implements Initializable {

    public TableView<SettleScoreBoard> scoreBoardTable;
    public TableColumn<SettleScoreBoard, String> game;
    public TableColumn<SettleScoreBoard, String> team1;
    public TableColumn<SettleScoreBoard, String> team2;
    public TableColumn<SettleScoreBoard, String> winners;
    public Button nextClick;
    public Button toHomeClick;
    ObservableList<SettleScoreBoard> scoreBoardData = FXCollections.observableArrayList();

    public SettleStageController() {
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

    public void initScoreBoard(WhistImpl whist, HashMap<String, String> scoreMap) {
        scoreBoardData = whist.getScoreBoardData();
        game.setCellValueFactory(cellData -> cellData.getValue().deckRoundProperty());
        team1.setCellValueFactory(cellData -> cellData.getValue().teamScoreProperty());
        team2.setCellValueFactory(cellData -> cellData.getValue().opponentScoreProperty());
        winners.setCellValueFactory(cellData -> cellData.getValue().winnersProperty());

        String team1Name = "You & " + whist.playerList.get(2).getId();
        String team2Name = whist.playerList.get(1).getId() + " & " + whist.playerList.get(3).getId();
        String deckWinnersName;
        if (Integer.parseInt(scoreMap.get("uAndTeammateScore")) > Integer.parseInt(scoreMap.get("opponentScore"))) {
            deckWinnersName = team1Name;
        } else {
            deckWinnersName = team2Name;
        }
        String totalWinnersName;
        if (Integer.parseInt(scoreMap.get("totalScore")) > Integer.parseInt(scoreMap.get("opponentScore"))) {
            totalWinnersName = team1Name;
        } else {
            totalWinnersName = team2Name;
        }
        team1.setText(team1Name);
        team2.setText(team2Name);
        scoreBoardTable.setItems(scoreBoardData);
        SettleScoreBoard scoreBoard = new SettleScoreBoard(scoreMap.get("deckRound"), scoreMap.get("uAndTeammateScore"), scoreMap.get("opponentScore"), deckWinnersName);
        SettleScoreBoard total = new SettleScoreBoard("Total", scoreMap.get("uAndTeammateScoreTotal"), scoreMap.get("opponentScoreTotal"), totalWinnersName);
        scoreBoardData.add(scoreBoard);
        if (scoreBoardData.size() > 1) {
            scoreBoardData.remove(scoreBoardData.size() - 1);
        }
        scoreBoardData.add(total);

    }

    public void nextClick(ActionEvent actionEvent) {
    }

    public void homeClick(ActionEvent actionEvent) {
    }
}