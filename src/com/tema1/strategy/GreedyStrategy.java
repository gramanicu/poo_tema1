package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.player.Bag;

import java.util.ArrayList;

public class GreedyStrategy implements Strategy {
    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @return The bag with the selected items
     */
    @Override
    public Bag createBag(final ArrayList<Goods> cards) {
        return new Bag();
    }

    @Override
    public void inspect() {

    }
}
