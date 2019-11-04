package com.tema1.strategy;

import com.tema1.goods.Goods;

class FrequencyPair {
    private Goods item;
    private Integer frequency;

    FrequencyPair(final Goods item, final Integer frequency) {
        this.item = item;
        this.frequency = frequency;
    }

    Integer getFrequency() {
        return frequency;
    }
    Goods getItem() {
        return item;
    }

}
