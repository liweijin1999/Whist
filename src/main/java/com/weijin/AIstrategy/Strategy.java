package com.weijin.AIstrategy;

import com.weijin.model.AbstractWhist;
import com.weijin.model.Card;
import com.weijin.model.Deck;
import com.weijin.model.Player;

import java.io.IOException;

public interface Strategy {
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) throws IOException, ClassNotFoundException;
}
