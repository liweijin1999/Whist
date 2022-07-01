package com.weijin.whistdemo.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

public class WhistImpl extends AbstractWhist {
    public List<Player> playerList = new ArrayList<>(4);
    public Integer deckRound = 0;
    ObservableList<SettleScoreBoard> scoreBoardData = FXCollections.observableArrayList();

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
    }
}