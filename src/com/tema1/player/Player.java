package com.tema1.player;

import com.tema1.goods.Goods;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.strategy.BaseStrategy;
import com.tema1.strategy.BribeStrategy;
import com.tema1.strategy.GreedyStrategy;
import com.tema1.strategy.Strategy;
import com.tema1.strategy.StrategyType;

import java.util.ArrayList;

public class Player {
    private int money;
    private static int playerCount = 0;
    private Strategy strategy;
    private RoleType role;
    private ArrayList<Goods> cards;
    private Bag bag;


    private Player() {
        money = Constants.START_MONEY;
        playerCount++;
    }

    public Player(final StrategyType strategy) {
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(final RoleType role) {
        this.role = role;
    }

    public void becomeSheriff() {
        this.role = RoleType.Sheriff;
    }

    public void becomeTrader() {
        this.role = RoleType.Trader;
    }


    private void setStrategy(final StrategyType strategy) {
        if (strategy == StrategyType.Base) {
            this.strategy = new BaseStrategy();
        } else if (strategy == StrategyType.Bribe) {
            this.strategy = new BribeStrategy();
        } else if (strategy == StrategyType.Greedy) {
            this.strategy = new GreedyStrategy();
        }
    }

    public void drawCards(final ArrayList<Goods> items) {
        cards.addAll(items);
    }
    public void createBag() {}
    public void declareGoods() {}
    public void inspect() {}

    public void prepareNextRound() {
        cards.clear();
        bag.clear();
    }


}

