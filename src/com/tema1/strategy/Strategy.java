package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.player.Bag;

import java.util.ArrayList;

public interface Strategy {
    Bag createBag(ArrayList<Goods> cards);

    void inspect();
}
