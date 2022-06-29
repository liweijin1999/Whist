package com.weijin.whistdemo.component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Deck {
    //每轮游戏牌桌上的基本信息，公开
    //例如：牌桌上的牌数量，牌桌上剩余的牌（已经出过的牌）
    //游戏开始52张牌，游戏结束时没有牌
    //各个玩家的出牌情况，剩余手牌数量
    public static final int DECK_SIZE = 13 * 4;
    private Suit currentTrump;
    private Suit currentLeadSuit = null;
    private List<Suit> trumpList = List.of(Suit.SPADES, Suit.HEARTS, Suit.CLUBS, Suit.DIAMONDS);
    private List<Player> playerList;
    private List<Player> turnList;
    private HashMap<Player, Card> thisRoundCards = new HashMap<Player, Card>();
    public int cardsLeftOnDeck = DECK_SIZE;

    public Deck(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Player> getTurnList() {
        return turnList;
    }

    public int getCardAtDeck() {
        return this.cardsLeftOnDeck;
    }

    public void cardThrowed() {
        this.cardsLeftOnDeck--;
    }

    public boolean isDeckEmpty() {
        return this.cardsLeftOnDeck <= 0;
    }

    public Suit getCurrentTrump() {
        return currentTrump;
    }

    public void setCurrentTrump(Suit currentTrump) {
        this.currentTrump = currentTrump;
    }

    public void setCurrentTrumpByRound(int round) {
        switch (round % 5) {
            case 0 -> this.currentTrump = null;
            case 1 -> this.currentTrump = this.trumpList.get(0);
            case 2 -> this.currentTrump = this.trumpList.get(1);
            case 3 -> this.currentTrump = this.trumpList.get(2);
            case 4 -> this.currentTrump = this.trumpList.get(3);
        }
    }

    public Suit getCurrentLeadSuit() {
        return currentLeadSuit;
    }

    public void setCurrentLeadSuit(Suit currentLeadSuit) {
        this.currentLeadSuit = currentLeadSuit;
    }

    public HashMap<Player, Card> getThisRoundCards() {
        return thisRoundCards;
    }

    public void addThisRoundCard(Player player, Card thisRoundCard) {
        this.thisRoundCards.put(player, thisRoundCard);
    }

    public int getCardsLeftOnDeck() {
        return cardsLeftOnDeck;
    }

    public void setCardsLeftOnDeck(int cardsLeftOnDeck) {
        this.cardsLeftOnDeck = cardsLeftOnDeck;
    }

    public HashMap<Player, Card> getBiggestThisRound() {
        HashMap<Player, Card> biggestThisRound = new HashMap<Player, Card>();
        int max = 0;
        for (Player player : thisRoundCards.keySet()) {
            int value = 0;
            Card card = thisRoundCards.get(player);
            if (card.getSuit() == this.currentTrump) {
                value = card.getRank().getCardValue() * 100;
            } else {
                value = card.getRank().getCardValue();
            }
            if (value > max) {
                max = value;
                biggestThisRound.clear();
                biggestThisRound.put(player, card);
            }
        }
        return biggestThisRound;
    }

    public void dealCards(List<Player> players) {
        Dealer dealer = new Dealer();
        dealer.deal(players);
    }

    public void initNewDeckTurn() {
        Random random = new Random();
        int starter = random.nextInt(4);
        setTurnListByStarter(starter, false);
    }

    public void setTurnListByStarter(int starter, boolean toSetTurn) {
        switch (starter) {
            case 0 ->
                    turnList = Arrays.asList(playerList.get(0), playerList.get(1), playerList.get(2), playerList.get(3));
            case 1 ->
                    turnList = Arrays.asList(playerList.get(1), playerList.get(2), playerList.get(3), playerList.get(0));
            case 2 ->
                    turnList = Arrays.asList(playerList.get(2), playerList.get(3), playerList.get(0), playerList.get(1));
            case 3 ->
                    turnList = Arrays.asList(playerList.get(3), playerList.get(0), playerList.get(1), playerList.get(2));
            default -> turnList = playerList;
        }
        if (toSetTurn) {
            turnList.get(1).setTurn(false);
            turnList.get(2).setTurn(false);
            turnList.get(3).setTurn(false);
            turnList.get(0).setTurn(true);

            currentLeadSuit = null;
        }
    }

    public void initNewDeckTrump() {
        this.currentTrump = this.trumpList.get(0);
    }

    public void initNewDeck() {
        initNewDeckTurn();
        initNewDeckTrump();
    }

    public boolean isAllowed(Player player, Card card, Deck deck) {
        if (currentLeadSuit == null) {
            return true;
        }
        boolean hasCurrLeadSuit = false;
        for (Card c : player.getCurrHand()) {
            if (c.getSuit() == deck.getCurrentLeadSuit()) {
                hasCurrLeadSuit = true;
                break;
            }
        }
        if (hasCurrLeadSuit && deck.getCurrentLeadSuit() == card.getSuit()) {
            return true;
        } else return !hasCurrLeadSuit;
    }
}
