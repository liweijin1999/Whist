package com.weijin.whist.AIstrategy;

import com.weijin.whist.model.AbstractWhist;
import com.weijin.whist.model.Card;
import com.weijin.whist.model.Deck;
import com.weijin.whist.model.Player;

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

