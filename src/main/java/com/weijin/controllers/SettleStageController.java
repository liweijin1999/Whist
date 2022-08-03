package com.weijin.controllers;

import com.weijin.AboutStage;
import com.weijin.MainStage;
import com.weijin.RuleStage;
import com.weijin.model.SettleScoreBoard;
import com.weijin.model.WhistImpl;
import com.weijin.singleton.FileLogger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.weijin.utils.helper.rankToSymbol;
import static com.weijin.utils.helper.suitToSymbol;

public class SettleStageController implements Initializable {
    public Label congratsLabel;
    private WhistImpl whist;
    public TableView<SettleScoreBoard> scoreBoardTable;
    public TableColumn<SettleScoreBoard, String> game;
    public TableColumn<SettleScoreBoard, String> team1;
    public TableColumn<SettleScoreBoard, String> team2;
    public TableColumn<SettleScoreBoard, String> winners;
    public Button nextClick;
    public Button toHomeClick;
    ObservableList<SettleScoreBoard> scoreBoardData = FXCollections.observableArrayList();
    private GamingStageController gamingStageController;

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
//        game.setCellValueFactory(cellData -> cellData.getValue().deckRoundProperty());
//        team1.setCellValueFactory(cellData -> cellData.getValue().teamScoreProperty());
//        team2.setCellValueFactory(cellData -> cellData.getValue().opponentScoreProperty());
//        winners.setCellValueFactory(cellData -> cellData.getValue().winnersProperty());
//        scoreBoardTable.setItems(scoreBoardData);
//        SettleScoreBoard scoreBoard1 = new SettleScoreBoard("example1","1","2","3");
//        SettleScoreBoard scoreBoard2 = new SettleScoreBoard("example2","1","2","3");
//        SettleScoreBoard scoreBoard3 = new SettleScoreBoard("example3","1","2","3");
//        scoreBoardData.add(scoreBoard1);
//        scoreBoardData.add(scoreBoard2);
//        scoreBoardData.add(scoreBoard3);
//        congratsLabel.setText("Oops! You just lost, Come on next round!");
    }

    public void initScoreBoard(WhistImpl whist, HashMap<String, String> scoreMap) {
        this.whist = whist;
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
            congratsLabel.setText("Congrats! You win this round!");
        } else {
            deckWinnersName = team2Name;
            congratsLabel.setText("Oops! You just lost, Come on next round!");
        }
        String totalWinnersName;
        if (Integer.parseInt(scoreMap.get("uAndTeammateScoreTotal")) > Integer.parseInt(scoreMap.get("opponentScoreTotal"))) {
            totalWinnersName = team1Name;
        } else if (Integer.parseInt(scoreMap.get("uAndTeammateScoreTotal")) < Integer.parseInt(scoreMap.get("opponentScoreTotal"))) {
            totalWinnersName = team2Name;
        } else {
            totalWinnersName = "Tie";
        }
        team1.setText(team1Name);
        team2.setText(team2Name);
        scoreBoardTable.setItems(scoreBoardData);
        SettleScoreBoard scoreBoard = new SettleScoreBoard(scoreMap.get("deckRound"), scoreMap.get("uAndTeammateScore"), scoreMap.get("opponentScore"), deckWinnersName);
        SettleScoreBoard total = new SettleScoreBoard("Total", scoreMap.get("uAndTeammateScoreTotal"), scoreMap.get("opponentScoreTotal"), totalWinnersName);
        FileLogger obj = FileLogger.getFileLogger();
        obj.write("\nYourTeam Points:" + scoreMap.get("uAndTeammateScoreTotal") + "\nAdversaries' Points:" + scoreMap.get("opponentScoreTotal") + "\n");
        obj.close();
        if (scoreBoardData.size() > 1) {
            scoreBoardData.remove(scoreBoardData.size() - 1);
        }
        scoreBoardData.add(scoreBoard);
        scoreBoardData.add(total);
    }

    public void nextClick(ActionEvent actionEvent) throws Exception {
        gamingStageController.initController(whist);
        Stage stage = (Stage) nextClick.getScene().getWindow();
        stage.close();

    }

    public void homeClick(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit?");
        alert.setContentText("""
                Are you sure to leave this page?
                        
                Warning: Your progress may be lost if you do so.""");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Confirm");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
        ok.setOnAction(event -> {
            MainStage mainStage = new MainStage();
            Stage stage = (Stage) nextClick.getScene().getWindow();
            stage.close();
            try {
                mainStage.showWindow();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        alert.show();
    }

    public void newGameClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Restart a game?");
        alert.setContentText("""
                Are you sure you want to start a brand new game?

                Warning: All of your progress will be lost.

                If not, please click "Next Game" button on this page to continue.""");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Confirm");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
        ok.setOnAction(event -> {
            MainStage mainStage = new MainStage();
            try {
                mainStage.showWindow();
                Stage stage = (Stage) toHomeClick.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        alert.show();
    }

    public void exit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit?");
        alert.setContentText("""
                Are you sure to fully exit this application?
                        
                Warning: All of your progress will be lost.""");
        Button ok = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        ok.setText("Confirm");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Cancel");
        ok.setOnAction(event -> {
            Platform.exit();
        });
        alert.show();

    }

    public void showRules(ActionEvent actionEvent) throws Exception {
        RuleStage ruleStage = new RuleStage();
        ruleStage.showWindow();
    }

    public void showAbout(ActionEvent actionEvent) throws Exception {
        AboutStage aboutStage = new AboutStage();
        aboutStage.showWindow();
    }

    public void initController(GamingStageController gsc) {
        this.gamingStageController = gsc;
    }
}