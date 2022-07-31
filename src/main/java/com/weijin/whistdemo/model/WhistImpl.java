package com.weijin.whistdemo.model;

import com.weijin.whistdemo.singleton.FileLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

import static com.weijin.whistdemo.utils.helper.rankToSymbol;

public class WhistImpl extends AbstractWhist {
    public List<Player> playerList = new ArrayList<>(4);
    public Integer deckRound = 0;
    ObservableList<SettleScoreBoard> scoreBoardData = FXCollections.observableArrayList();
    private Integer difficulty;

    public ObservableList<SettleScoreBoard> getScoreBoardData() {
        return scoreBoardData;
    }

    @Override
    public List<Player> loadPlayers(Player you) {
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();
        p2.setId("player2");
        p3.setId("player3");
        p4.setId("player4");
        playerList = Arrays.asList(you, p2, p3, p4);
        return playerList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void addDeckRound() {
        deckRound++;
        FileLogger obj = FileLogger.getFileLogger();
        obj.write("Game  " + deckRound);
        obj.close();
    }

    public void setScoreBoardData(ObservableList<SettleScoreBoard> scoreBoardData) {
        this.scoreBoardData = scoreBoardData;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }
}
