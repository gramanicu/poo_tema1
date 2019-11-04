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

    public void addItem(final Goods item) {
        goodsList.add(item);
    }

    public void setBribe(final int value) {
        bribe = value;
    }

    public ArrayList getItems() {
        return goodsList;
    }

    public void clear() {
        goodsList.clear();
        bribe = 0;
    }
}
