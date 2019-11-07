package com.tema1.strategy;

import com.tema1.game.Game;
import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.player.Bag;
import com.tema1.player.Player;

import java.util.ArrayList;
import java.util.Queue;

public class GreedyStrategy extends BaseStrategy {
    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @param money The amount of money the player has available (for bribes and penalties)
     * @return The bag with the selected items
     */
    @Override
    public Bag createBag(final ArrayList<Goods> cards,final int money) {
        Bag bag = super.createBag(cards, money);

        if (Game.getInstance().isOddRound()) {
            if (bag.getItems().size() < Constants.MAX_BAG_SIZE && bag.getItems().size() > 0) {
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

    /**
     * The sheriff will inspect the bags of the other players.
     * @param players The list of players in the game
     * @param cardsDeck The current deck of cards on the "table"
     */
    @Override
    public void inspect(final ArrayList<Player> players, final Queue<Goods> cardsDeck) {
        Player sheriff = getSheriff(players);

        for (Player player : players) {
            if (player.getRole() != RoleType.Sheriff) {
                Bag bag = player.getBag();
                // Check if he has enough money for a inspection
                if (sheriff.getMoney() >= Constants.MIN_MONEY_FOR_INSPECTION) {
                    if (!bag.hasBribe()) {
                        sheriff.setMoney(bag.inspect(sheriff.getMoney(), cardsDeck));
                    } else {
                        sheriff.setMoney(sheriff.getMoney() + bag.takeBribe());
                    }
                }
            }
        }
    }

}
