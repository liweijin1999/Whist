package com.weijin.whist.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SettleScoreBoard {
    private StringProperty deckRound = new SimpleStringProperty();
    private StringProperty teamScore = new SimpleStringProperty();
    private StringProperty opponentScore = new SimpleStringProperty();
    private StringProperty winners = new SimpleStringProperty();

    public SettleScoreBoard(String deckRound, String teamScore, String opponentScore, String winners) {
        this.setDeckRound(deckRound);
        this.setTeamScore(teamScore);
        this.setOpponentScore(opponentScore);
        this.setWinners(winners);
    }

    public String getDeckRound() {
        return deckRoundProperty().get();
    }

    public StringProperty deckRoundProperty() {
        return deckRound;
    }

    public void setDeckRound(String deckRound) {
        this.deckRoundProperty().set(deckRound);
    }

    public String getTeamScore() {
        return teamScoreProperty().get();
    }

    public StringProperty teamScoreProperty() {
        return teamScore;
    }

    public void setTeamScore(String teamScore) {
        this.teamScoreProperty().set(teamScore);
    }

    public String getOpponentScore() {
        return opponentScoreProperty().get();
    }

    public StringProperty opponentScoreProperty() {
        return opponentScore;
    }

    public void setOpponentScore(String opponentScore) {
        this.opponentScoreProperty().set(opponentScore);
    }

    public String getWinners() {
        return winnersProperty().get();
    }

    public StringProperty winnersProperty() {
        return winners;
    }

    public void setWinners(String winners) {
        this.winnersProperty().set(winners);
    }
}
