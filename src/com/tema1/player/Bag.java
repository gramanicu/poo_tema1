package com.tema1.player;

import com.tema1.goods.Goods;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Goods> goodsList;
    private int bribe;

    public Bag() {
        goodsList = new ArrayList<>();
        bribe = 0;
    }

    /**
     * Add an item to the bag.
     * @param item The item to be added
     */
    public void addItem(final Goods item) {
        goodsList.add(item);
    }

    /**
     * Add a bribe to the current bag.
     * @param value The bribe to be added
     */
    public void setBribe(final int value) {
        bribe = value;
    }

    /**
     * Get the items in the bag.
     * @return The contained items
     */
    public ArrayList getItems() {
        return goodsList;
    }

    /**
     * Create a new bag, from a list of items.
     * @param items The items to be added.
     */
    public void setItems(final ArrayList<Goods> items) {
        goodsList.clear();
        goodsList.addAll(items);
    }

    /**
     * Remove all items from the bag, including the bribe.
     */
    public void clear() {
        goodsList.clear();
        bribe = 0;
    }
}
