package com.weijin.whistdemo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RuleStageController implements Initializable {
    public Label ruleText;
    public Button okBtn;

    public RuleStageController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ruleText.setText("Whist Rules\n" +
                "Whist is a simple trick taking game, played in pairs. The players sitting across from each other are " +
                "\na team and together they try to get as many tricks as possible.\n" +
                "\n" +
                "Dealing and starting\n" +
                "Each player gets dealt 13 cards. The first player to start is chosen randomly, in the next round the " +
                "\nperson to start will be the next person to the left of the person who started the current round. " +
                "\n(1 round is 13 tricks, i.e. starting a new round is when all the cards are finished and are dealt " +
                "\nagain).\n" +
                "\n" +
                "Trumps\n" +
                "In each round there is a special trump suit, whose cards are considered higher than all the other suits. Every fifth round there is no trump. The order of trumps goes: Hearts, Spades, Diamonds, Clubs, No Trump.\n" +
                "\n" +
                "Playing\n" +
                "A player leading a trick can put out a card in any suit he wants, even the trump suit. The players that follow must put out cards in the same suit if they have at least one. If they have no cards in the same suit they may put out any card they want. The player who puts out the highest card in the suit takes the trick, unless someone has put out a trump card, in which case the highest trump card takes it. The player who takes the trick will then lead in the next trick.\n" +
                "\n" +
                "Scoring\n" +
                "After a round is finished the score is calculated. The tricks of each team are counted, and they get a point for each trick over 6 tricks. So if Mike and Lisa get 8 tricks and You and Bill get 5, then Mike and Lisa get 2 points but You and Bill get no points. Points are tracked between rounds and the first team to get 7 points wins the entire game. Since there are 13 tricks in each round and you get points for number of tricks above 6 that means that if you get all 13 tricks you will be able to win in one round.");
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
}
