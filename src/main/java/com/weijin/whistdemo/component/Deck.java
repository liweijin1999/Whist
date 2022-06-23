package com.weijin.whistdemo.component;

public class Deck {
    //牌桌上的基本信息，公开
    //例如：牌桌上的牌数量，牌桌上剩余的牌（已经出过的牌）
    //游戏开始52张牌，游戏结束时没有牌
    //各个玩家的出牌情况，剩余手牌数量
    public static final int DECK_SIZE = 13 * 4;
    public static final int HAND_SIZE = 13;
    public static final int PLAYER_NUMBER = 4;
    private Suit currentTrump;
    private Suit currentLeadSuit;
    public int cardsOnDeck = DECK_SIZE;
    public int cardsPlayed = 0;

    public int getCardAtDeck() {
        return this.cardsOnDeck;
    }

    public void cardThrowed() {
        this.cardsOnDeck--;
    }

    public boolean isDeckEmpty() {
        return this.cardsOnDeck == 0;
    }

    public Suit getCurrentTrump() {
        return currentTrump;
    }

    public void setCurrentTrump(Suit currentTrump) {
        this.currentTrump = currentTrump;
    }

    public Suit getCurrentLeadSuit() {
        return currentLeadSuit;
    }

    public void setCurrentLeadSuit(Suit currentLeadSuit) {
        this.currentLeadSuit = currentLeadSuit;
    }

}
