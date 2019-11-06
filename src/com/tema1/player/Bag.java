package com.tema1.player;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.helpers.Constants;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Goods> goodsList;
    private int bribe;
    private Goods declaredType;

    public Bag() {
        goodsList = new ArrayList<>();
        bribe = 0;
    }

    // Copy - Constructor
    public Bag(final Bag other) {
        this.goodsList = new ArrayList<>();
        this.goodsList.addAll(other.getItems());
        this.bribe = other.bribe;
        this.declaredType = GoodsFactory.getInstance().getGoodsById(other.declaredType.getId());
    }

    /**
     * Add an item to the bag.
     * @param item The item to be added
     */
    public void addItem(final Goods item) {
        goodsList.add(item);
    }

    /**
     * Adds multiple items of the same type to the bag.
     * @param item The item to be added (apple, etc. )
     * @param count The number of items
     */
    public void addItem(final Goods item, final int count) {
        int limitedCount = count;
        if (count > Constants.MAX_BAG_SIZE) {
            limitedCount = Constants.MAX_BAG_SIZE;
        }

        for (int i = 0; i < limitedCount; i++) {
            this.addItem(item);
        }
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
    public ArrayList<Goods> getItems() {
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
     * What the items are declared as.
     * @param type The goods type
     */
    public void declareItems(final Goods type) {
        declaredType = type;
    }

    /**
     * Remove all items from the bag, including the bribe.
     */
    public void clear() {
        goodsList.clear();
        bribe = 0;
    }
}
