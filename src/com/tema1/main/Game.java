package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.player.Player;
import com.tema1.strategy.StrategyType;

import java.util.ArrayList;

// This must be unique
final class Game {
    private ArrayList<Player> playerList;
    private ArrayList<Goods> goodsList;
    private int rounds;
    private int currentRound;
    private GoodsFactory assetCreator;
    private boolean canRun;

    Game() {
        currentRound = 0;
        playerList = new ArrayList<>();
        goodsList = new ArrayList<>();
        assetCreator = new GoodsFactory();
    }

    void load(GameInput data) {
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
        canRun = true;
    }

    void run() {
        if (!canRun) {
            return;
        }

        while (currentRound < rounds) {
            bagCreation();
            goodsDeclaration();
            inspection();
            shopSupplying();
            rounds++;
        }


    }

    private void bagCreation() {

    }

    private void goodsDeclaration() {}

    private void inspection() {}

    private void shopSupplying() {}

}
