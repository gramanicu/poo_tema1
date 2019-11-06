package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.player.Bag;
import com.tema1.player.Player;

import java.util.ArrayList;
import java.util.Queue;

public interface Strategy {
    Bag createBag(ArrayList<Goods> cards);

    void inspect(ArrayList<Player> players, Queue<Goods> cardsDeck);
}
