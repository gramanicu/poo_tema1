package com.tema1.strategy;

import com.tema1.goods.Goods;

import java.util.Comparator;

public class ItemValueComparator implements Comparator<Goods> {
    /**
     * Compares two goods using the value they have.
     * @param o1 The first good
     * @param o2 The second good
     * @return The difference of value between the second and the first (descending sort)
     */
    @Override
    public int compare(final Goods o1, final Goods o2) {
        return o2.getProfit() - o1.getProfit();
    }
}
