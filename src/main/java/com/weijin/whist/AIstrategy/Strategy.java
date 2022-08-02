package com.weijin.whist.AIstrategy;

import com.weijin.whist.model.AbstractWhist;
import com.weijin.whist.model.Card;
import com.weijin.whist.model.Deck;
import com.weijin.whist.model.Player;

import java.io.IOException;

public interface Strategy {
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame) throws IOException, ClassNotFoundException;
}
