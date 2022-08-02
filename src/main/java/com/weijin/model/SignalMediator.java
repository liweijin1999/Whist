package com.weijin.model;

import java.util.List;

public class SignalMediator {
    Deck deck;
    List<Player> playerList;

    public SignalMediator(Deck deck) {
        this.deck = deck;
        this.playerList = deck.getPlayerList();
    }

    public void hasNoLeadSuitSignalChange(Player player) {
        switch (deck.getCurrentLeadSuit()) {
            case SPADES -> player.hasNoSpadeSignal = true;
            case HEARTS -> player.hasNoHeartSignal = true;
            case CLUBS -> player.hasNoClubSignal = true;
            case DIAMONDS -> player.hasNoDiamondSignal = true;
        }
    }
}
