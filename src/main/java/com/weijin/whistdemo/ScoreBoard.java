package com.weijin.whistdemo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScoreBoard {
    public ScoreBoard(String id, String name) {
        this.setuAndTeammateScore(id);
        this.setName(name);
    }

    private StringProperty uAndTeammateScore;  //也可以在这里直接new

    public void setuAndTeammateScore(String value) {
        uAndTeammateScoreProperty().set(value);
    }

    public String getuAndTeammateScore() {
        return uAndTeammateScoreProperty().get();
    }

    public StringProperty uAndTeammateScoreProperty() {
        if (uAndTeammateScore == null) uAndTeammateScore = new SimpleStringProperty(this, "uAndTeammateScore");
        return uAndTeammateScore;
    }

    private StringProperty opponentScore;

    public void setName(String value) {
        nameProperty().set(value);
    }

    public String getName() {
        return uAndTeammateScoreProperty().get();
    }

    public StringProperty nameProperty() {
        if (opponentScore == null) opponentScore = new SimpleStringProperty(this, "opponentScore");
        return opponentScore;
    }
}