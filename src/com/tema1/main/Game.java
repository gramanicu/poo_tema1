package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.strategy.StrategyType;

import java.util.ArrayList;
import java.util.List;

// This must be unique
public final class Game {
    private List<Player> playerList;
    private List<Goods> goodsList;

    private int rounds;
    private int currentRound;

    private GoodsFactory assetCreator;

    Game() {
        currentRound = 0;
        playerList = new ArrayList<>();
        goodsList = new ArrayList<>();
        assetCreator = new GoodsFactory();

    }

    public void load(GameInput data) {
    // This must be unique
        // Load players strategies
        for (String strategy : data.getPlayerNames()) {
            switch (strategy) {
                case "basic":
                    playerList.add(new Player(StrategyType.Base));
                    break;
                case "bribed":
                    playerList.add(new Player(StrategyType.Bribe));
                    break;
                case "greedy":
                    playerList.add(new Player(StrategyType.Greedy));
                    break;
            }
        }

        // Load assets cards
        for (int id : data.getAssetIds()) {
            goodsList.add(assetCreator.getGoodsById(id));
        }

        // Load the number of rounds to be played
        rounds = data.getRounds();
    }

    public void run() {

    }
}
