package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.player.Bag;
import com.tema1.player.Player;

import java.util.ArrayList;
import java.util.Queue;

public class BribeStrategy implements Strategy {

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
    public void inspect(final ArrayList<Player> players, final Queue<Goods> cardsDeck) {

    }


}
