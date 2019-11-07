package com.tema1.strategy;

import com.tema1.game.Game;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;
import com.tema1.player.Bag;

import java.util.ArrayList;

public class GreedyStrategy extends BaseStrategy {
    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @return The bag with the selected items
     */
    @Override
    public Bag createBag(final ArrayList<Goods> cards) {
        Bag bag = super.createBag(cards);

        if (!Game.getInstance().isOddRound()) {
            if (bag.getItems().size() < Constants.MAX_BAG_SIZE && bag.getItems().size() > 1) {
                // If we can add a illegal good
                ArrayList<Goods> uniqueItems = new ArrayList<>();

                for (Goods item : cards) {
                    if (!uniqueItems.contains(item) && item.getType() == GoodsType.Illegal) {
                        uniqueItems.add(item);
                    }
                }

                if (uniqueItems.size() != 0) {
                    ItemValueComparator valueCompare = new ItemValueComparator();
                    uniqueItems.sort(valueCompare);
                    bag.addItem(uniqueItems.get(0), 1);
                }
            }
        }

        return bag;
    }
}
