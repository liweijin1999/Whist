package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.component.*;

public class EasyStrategy implements Strategy {
    // very simple strategy: random strategy

    @Override
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) {
        for (Card card : player.getCurrHand()) {
            if (deck.isAllowed(player, card, deck)) {
                return card;
            }
        }
        return null;
    }
}

