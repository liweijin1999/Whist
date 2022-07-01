package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.model.AbstractWhist;
import com.weijin.whistdemo.model.Card;
import com.weijin.whistdemo.model.Deck;
import com.weijin.whistdemo.model.Player;

public interface Strategy {
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame);
}
