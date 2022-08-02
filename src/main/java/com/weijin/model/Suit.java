package com.weijin.model;

public enum Suit {
    CLUBS(2), DIAMONDS(3), HEARTS(1), SPADES(0);
    private int code;

    Suit(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Suit getSuit(int code) {
        for (Suit suit : Suit.values()) {
            if (suit.getCode() == code) {
                return suit;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Suit.getSuit(0).getCode());
    }
}
