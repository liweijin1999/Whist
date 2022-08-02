package com.weijin.AIstrategy;

import com.weijin.model.AbstractWhist;
import com.weijin.model.Card;
import com.weijin.model.Deck;
import com.weijin.model.Player;

public class EasyStrategy implements Strategy {
    // very simple strategy: random strategy

    @Override
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) {
        for (Card card : player.getCurrHand()) {
            if (deck.declare(player, card, deck)) {
                return card;
            }
        }
        return null;
    }
}

