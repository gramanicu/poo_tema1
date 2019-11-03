package com.tema1.main;

import com.tema1.helpers.Constants;
import com.tema1.strategy.*;

public class Player {
    private int money;
    private static int playerCount = 0;
    private Strategy strategy;

    Player() {
        money = Constants.START_MONEY;
        playerCount++;
    }

    Player(StrategyType strategy) {
        this();
        setStrategy(strategy);
    }

    public int getMoney() {
        return money;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public static boolean canAddPlayer() {
        return playerCount <= Constants.MAX_PLAYER_COUNT;
    }

    private void setStrategy(StrategyType strategy) {
        if( strategy == StrategyType.Base ) {
            this.strategy = new BaseStrategy();
        } else if (strategy == StrategyType.Bribe) {
            this.strategy = new BribeStrategy();
        } else if (strategy == StrategyType.Greedy) {
            this.strategy = new GreedyStrategy();
        }
    }

}

