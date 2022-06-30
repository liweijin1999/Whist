package com.weijin.whistdemo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScoreBoard {
    private StringProperty team;
    private StringProperty score;

    public ScoreBoard(String teamNames, String Score) {
        this.setTeam(teamNames);
        this.setScore(Score);
    }

    public void setTeam(String team) {
        teamProperty().set(team);
    }

    public String getTeam() {
        return teamProperty().get();
    }

    public StringProperty teamProperty() {
        if (team == null) team = new SimpleStringProperty(this, "team");
        return team;
    }

    public void setScore(String score) {
        scoreProperty().set(score);
    }

    public String getScore() {
        return scoreProperty().get();
    }

    public StringProperty scoreProperty() {
        if (score == null) score = new SimpleStringProperty(this, "score");
        return score;
    }
}