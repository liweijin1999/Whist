package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.*;

public class MediumSrtategy implements Strategy {
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
