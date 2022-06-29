package com.weijin.whistdemo.AIstrategy;

import com.weijin.whistdemo.component.AbstractWhist;
import com.weijin.whistdemo.component.Card;
import com.weijin.whistdemo.component.Deck;
import com.weijin.whistdemo.component.Player;

public interface Strategy {
    public Card AIStrategy(Player player, Deck deck, AbstractWhist cardGame);
}
