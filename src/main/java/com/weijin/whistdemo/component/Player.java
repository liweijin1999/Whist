package com.weijin.whistdemo.component;

public class Player {
    private String name;
    private int score = 0;
    private String avatar;
    private Card[] hand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Card[] getCurrHand() {
        return hand;
    }

    public void setInitHand(Card[] hand) {
        this.hand = hand;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getScore() {
        return this.score;
    }
}
