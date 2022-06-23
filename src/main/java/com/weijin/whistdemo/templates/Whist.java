package com.weijin.whistdemo.templates;

import com.weijin.whistdemo.component.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Whist extends CardGame {
    @Override
    public List<Player> loadPlayers(Player you) {
        Player p2 = new Player();
        Player p3 = new Player();
        Player p4 = new Player();
        return Arrays.asList(you, p2, p3, p4);
    }

    @Override
    public List<Player> initGame(List<Player> players) {
        //set starter, set trump, set turn direction
        Deck deck = new Deck();
        Random random = new Random();
        deck.setCurrentTrump(Suit.getSuit(random.nextInt(4)));
        List<Player> turnList;
        int starter = random.nextInt(4);
        switch (starter) {
            case 0 -> turnList = Arrays.asList(players.get(0), players.get(1), players.get(2), players.get(3));
            case 1 -> turnList = Arrays.asList(players.get(1), players.get(2), players.get(3), players.get(0));
            case 2 -> turnList = Arrays.asList(players.get(2), players.get(3), players.get(0), players.get(1));
            case 3 -> turnList = Arrays.asList(players.get(3), players.get(0), players.get(1), players.get(2));
            default -> turnList = players;
        }
        return turnList;
    }

    @Override
    public void dealCards(List<Player> players) {
        Dealer dealer = new Dealer();
        dealer.deal(players);
    }

    @Override
    public Card selectAcard(Card card) {
        return card;
    }

    @Override
    public boolean isAllowed(Card card, Deck deck) {
        if (deck.getCurrentLeadSuit() == card.getSuit()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void throwAcard(Card card) {

    }

    @Override
    public void addAtrick(Player player, Card card) {

    }

    @Override
    public boolean isDeckEmpty() {
        return false;
    }

    @Override
    public Card getBiggestCard() {
        return null;
    }

    @Override
    public void getResult() {

    }
}
