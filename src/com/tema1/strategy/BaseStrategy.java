package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.player.Bag;
import com.tema1.player.Player;

import java.util.ArrayList;
import java.util.Queue;

public class BaseStrategy implements Strategy {
    public BaseStrategy() { }


    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @return The bag with the selected items
     */
    @Override
    public Bag createBag(final ArrayList<Goods> cards) {
        Bag bag = new Bag();
        // Gets the best item/items available for the strategy
        FrequencyPair item = chooseGoods(cards);

        // If the item type is illegal, use the most profitable illegal item
        if (item.getItem().getType() == GoodsType.Illegal) {
            // Declare the bag to contain apples
            bag.declareItems(GoodsFactory.getInstance().getGoodsById(0));
        } else {
            bag.declareItems(GoodsFactory.getInstance().getGoodsById(item.getItem().getId()));
        }

        // Never bribe ^_^
        bag.setBribe(0);

        // Adds the item/items to the bag
        bag.addItem(GoodsFactory.getInstance()
                .getGoodsById(item.getItem().getId()), item.getFrequency());

        return bag;
    }

    /**
     * The sheriff will inspect the bags of the other players.
     * @param players The list of players in the game
     * @param cardsDeck The current deck of cards on the "table"
     */
    @Override
    public void inspect(final ArrayList<Player> players, final Queue<Goods> cardsDeck) {
        Player sheriff = null;
        int sheriffIndex;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole() == RoleType.Sheriff) {
                sheriff = players.get(i);
                sheriffIndex = i;
                break;
            }
        }

        if (sheriff == null) {
            return;
        }

        for (Player player : players) {
            if (player.getRole() != RoleType.Sheriff) {
                Bag bag = player.getBag();
                if (sheriff.getMoney() >= Constants.MIN_MONEY_FOR_INSPECTION) {
                    sheriff.setMoney(bag.inspect(sheriff.getMoney(), cardsDeck));
                }
            }
        }
    }

    /**
     * Returns item/items, according to the strategy.
     * @param cards The items to be sorted/filtered
     * @return A frequency pair, representing a item type and how many of it
     */
    protected FrequencyPair chooseGoods(final ArrayList<Goods> cards) {
        ArrayList<Goods> uniqueItems = new ArrayList<>();
        ArrayList<Integer> frequency = new ArrayList<Integer>();

        /*
            Make two arrays, that will contain unique items and their respective
            frequency
         */
        for (Goods item : cards) {
            if (!uniqueItems.contains(item)) {
                uniqueItems.add(item);
                frequency.add(1);
            } else {
                int index = uniqueItems.indexOf(item);
                Integer value = frequency.get(index);
                value = value + 1;
                frequency.set(index, value);
            }
        }

        ArrayList<FrequencyPair> listToSort = new ArrayList<>();

        // Combine the two arrays into a single frequency array
        for (Goods item : uniqueItems) {
            listToSort.add(new FrequencyPair(item, frequency.get(uniqueItems.indexOf(item))));
        }

        // Sort the array, according to the strategy
        FrequencyPairComparator compare = new FrequencyPairComparator();
        listToSort.sort(compare);

        ArrayList<FrequencyPair> sortedList = new ArrayList<>();

        // Filter out the illegal goods
        for (FrequencyPair pair : listToSort) {
            if (pair.getItem().getType() == GoodsType.Legal) {
                sortedList.add(pair);
            }
        }

        // Check if there are any legal goods
        if (sortedList.size() == 0) {
            // If there are no legal goods
            ItemValueComparator valueCompare = new ItemValueComparator();
            uniqueItems.sort(valueCompare);
            return new FrequencyPair(uniqueItems.get(0), 1);
        } else {
            return sortedList.get(0);
        }

    }
}
