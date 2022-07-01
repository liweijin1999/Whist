package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.*;

public class HardStrategy implements Strategy {
    @Override
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) {
        return new Card(Suit.CLUBS, Rank.ACE);
    }
}
