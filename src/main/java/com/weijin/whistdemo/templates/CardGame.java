package com.weijin.whistdemo.templates;

import com.weijin.whistdemo.component.Card;
import com.weijin.whistdemo.component.Deck;
import com.weijin.whistdemo.component.Player;

import java.util.List;

public abstract class CardGame {
    public List<Player> turnList;

    public abstract List<Player> loadPlayers(Player youPlayer);

    public abstract List<Player> initGame(List<Player> players); //set starter, set trump, set turn direction

    public abstract void dealCards(List<Player> players);

    public abstract Card selectAcard(Card card);

    public abstract boolean isAllowed(Card card, Deck deck);

    public abstract void throwAcard(Card card);

    public abstract void addAtrick(Player player, Card card);

    public abstract boolean isDeckEmpty();

    public abstract Card getBiggestCard();

    public abstract void getResult();
//    public void templateMethod(){
//        loadPlayers();
//        initGame();
//        dealCards();
//        while (!isDeckEmpty()){
//            Card card=selectAcard();
//            Card biggestThisRound=null;
//            for (Player player : turnList) {
//                while (!isAllowed(card)){
//                    //popup a dialog to select a card
//                    card=selectAcard();
//                }
//                throwAcard(card);
//            }
//            biggestThisRound=getBiggestCard();
//            addAtrick(new Player(),biggestThisRound);
//        }
//        getResult();
//    }
}
