package com.weijin.whistdemo.model;

public enum Rank {

    ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);
    private final int value;

    Rank(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static Rank getRank(int value) {
        if (value == 0) {
            return KING;
        }
        for (Rank rank : Rank.values()) {
            if (rank.getValue() == value) {
                return rank;
            }
        }
        return null;
    }

    public int getCardValue() {
        if (this.value == 1) {
            return 14;
        } else {
            return this.value;
        }
    }

}
