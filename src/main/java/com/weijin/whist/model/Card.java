package com.weijin.whist.model;

public class Card {
    private final Suit suit;
    private final Rank rank;
    private final String id;
    private boolean isFaceUp = false;
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

    public int getValue() {
        return rank.getCardValue();
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

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }
}
