package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsType;
import com.tema1.player.Bag;

import java.util.ArrayList;
public class BaseStrategy implements Strategy {

    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @return The bag with the selected items
     */
    @Override
    public Bag createBag(final ArrayList<Goods> cards) {
        Bag bag = new Bag();
        bag.setItems(sortGoods(cards, GoodsType.Legal));
        return bag;
    }

    @Override
    public void declareGoods() {

    }

    @Override
    public void inspect() {

    }

    /**
     * Will sort the items. Can remove items that are not of the specified type.
     * @param cards The items to be sorted
     * @param type The type of the items that should be returned
     * @return The items, sorted by frequency and filtered to contain only the specified type
     */
    private ArrayList<Goods> sortGoods(final ArrayList<Goods> cards, final GoodsType type) {
        /* ToDo implement " by id " sorting, " by profit " sorting, and the edge case for
            when are not goods of the specified type
        */
        ArrayList<Goods> uniqueItems = new ArrayList<>();
        ArrayList<Integer> frequency = new ArrayList<Integer>();

        for (Goods item : cards) {
            if (uniqueItems.contains(item)) {
                uniqueItems.add(item);
                frequency.add(0);
            } else {
                int index = uniqueItems.indexOf(item);
                Integer value = frequency.get(index);
                value = value + 1;
                frequency.set(index, value);
            }
        }

        ArrayList<FrequencyPair> listToSort = new ArrayList<>();

        for (Goods item : uniqueItems) {
            listToSort.add(new FrequencyPair(item, frequency.get(uniqueItems.indexOf(item))));
        }

        FrequencyPairCompare compare = new FrequencyPairCompare();
        listToSort.sort(compare);

        ArrayList<Goods> sortedList = new ArrayList<>();

        for (FrequencyPair pair : listToSort) {
            if (pair.getItem().getType() == type || type == GoodsType.All) {
                sortedList.add(pair.getItem());
            }

            if (pair.getItem().getType() == type || type == GoodsType.All) {
                sortedList.add(pair.getItem());
            }
        }

        return sortedList;
    }
}
