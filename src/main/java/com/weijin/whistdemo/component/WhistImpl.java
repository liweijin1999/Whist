package com.weijin.whistdemo.component;

import java.util.*;

public class WhistImpl extends AbstractWhist {
    public List<Player> playerList = new ArrayList<>(4);
    private Deck currDeck;
    private HashMap<String, Integer> scoreMap = new HashMap<>();

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
}
