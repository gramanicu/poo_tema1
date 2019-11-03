package com.tema1.main;

import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.strategy.*;

public class Player {
    private int money;
    private static int playerCount = 0;
    private Strategy strategy;
    private RoleType role;


    private Player() {
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

    public RoleType getRole() { return role; }
    public void setRole(RoleType role) { this.role = role; }
    public void becomeSheriff() { this.role = RoleType.Sheriff; }
    public void becomeTrader() { this.role = RoleType.Trader; }

    private void setStrategy(StrategyType strategy) {
        if( strategy == StrategyType.Base ) {
            this.strategy = new BaseStrategy();
        } else if (strategy == StrategyType.Bribe) {
            this.strategy = new BribeStrategy();
        } else if (strategy == StrategyType.Greedy) {
            this.strategy = new GreedyStrategy();
        }
    }

    public void drawCards() {}
    public void createBag() {}
    public void declareGoods() {}
    public void inspect() {}


}

