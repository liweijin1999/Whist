package com.weijin.whistdemo.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private int score = 0;
    private String avatar;
    private List<Card> hand = new ArrayList<>();
    private List<Card> tricks = new ArrayList<>();
    private boolean turn = false;
    public PropertyChangeSupport psc = new PropertyChangeSupport(this);

    //    Player(String id,String avatar,boolean isAI){
//        this.id=id;
//        this.avatar=avatar;
//        this.isAI=isAI;
//    }
    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
        psc.firePropertyChange("hand", null, hand);
    }

    public List<Card> getCurrHand() {
        return hand;
    }

    public void setInitHand(List<Card> hand) {
        this.hand = hand;
    }

    public void throwCard(Card card) {
        List<Card> newHand = new ArrayList<>();
        int index = 0;
        for (Card value : hand) {
            if (value != card) {
                newHand.add(index, value);
                index++;
            }
        }
        this.hand = newHand;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Card> getTricks() {
        return tricks;
    }

    public void addTrick(Card trick) {
        this.tricks.add(trick);
    }

    public void setTricks(List<Card> tricks) {
        this.tricks = tricks;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        boolean previousTurn = this.turn;
        this.turn = turn;
        psc.firePropertyChange("setTurn_pro", previousTurn, this.turn);
    }
}
