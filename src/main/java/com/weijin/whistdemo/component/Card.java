package com.weijin.whistdemo.component;

import javafx.scene.image.Image;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private final String id;
    private boolean isFaceUp = false;
    private boolean isSelected = false;
    private boolean isPlayed = false;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        String _suit = switch (suit) {
            case CLUBS -> "club";
            case HEARTS -> "heart";
            case DIAMONDS -> "diamond";
            case SPADES -> "spade";
        };
        this.id = _suit + rank.getValue();

    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public String getId() {
        return id;
    }

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
}
