package com.weijin.whist.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RuleStageController implements Initializable {
    public Button okBtn;
    public Label ruleContent;
    public Label trumpContent;
    public Label playContent;
    public Label scoringContent;
    public Label refContent;

    public RuleStageController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ruleContent.setText("Whist is a classic English trick-taking card game where a standard 52-card pack is used.\nThe cards in each suit rank from highest to lowest: A K Q J 10 9 8 7 6 5 4 3 2.\nFour people can play in partnerships of two against two.");
        trumpContent.setText("Each player gets dealt 13 cards. The first player to start is chosen randomly.\nIn each round there is a special trump suit, whose cards are considered higher than all the other suits. Every fifth round there is no trump. The order of trumps goes: Hearts, Spades, Diamonds, Clubs, No Trump.");
        playContent.setText("The turn to play is in clockwise rotation. The player on the dealer's left leads first and may play any card. Each player in turn plays a card, following suit if possible. If you cannot follow suit, a player may play any card. Four cards played (including the card led) constitute a trick.\nA trick is won by the person who played the highest trump. Any trick not containing a trump is won by the person who played the highest card of the suit led. The winner of each trick leads next.");
        scoringContent.setText("Each odd trick (a trick in excess of six) counts one point for the side winning it. The partnership with the most points at the end of play wins the game.");
        refContent.setText("[1] \thttps://en.wikipedia.org/wiki/Whist\n[2]\thttps://cardgames.io/whist/\n[3]\thttps://bicyclecards.com/how-to-play/whist/");
    }

    public void exit(ActionEvent actionEvent) {
        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }
}
