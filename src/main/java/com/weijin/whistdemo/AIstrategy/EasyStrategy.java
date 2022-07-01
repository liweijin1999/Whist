package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.AbstractWhist;
import com.weijin.whistdemo.model.Card;
import com.weijin.whistdemo.model.Deck;
import com.weijin.whistdemo.model.Player;

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

