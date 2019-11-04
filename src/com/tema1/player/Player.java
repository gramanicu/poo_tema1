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

    /**
     * @return Current money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * @return The current number of players in the game
     */
    public static int getPlayerCount() {
        return playerCount;
    }

    /**
     * Checks if the current number of players is below the maximum allowed.
     * @return Whether or not a player can be added
     */
    public static boolean canAddPlayer() {
        return playerCount <= Constants.MAX_PLAYER_COUNT;
    }

    /**
     * @return The role of the player (Sheriff / Trader)
     */
    public RoleType getRole() {
        return role;
    }

    /**
     * Sets the role of the player (Sheriff / Trader).
     * @param role The role to assign
     */
    public void setRole(final RoleType role) {
        this.role = role;
    }

    /**
     *  The player will have the sheriff role.
     */
    public void becomeSheriff() {
        this.role = RoleType.Sheriff;
    }

    /**
     * The player will have a trader role.
     */
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

    /**
     * The player will draw the cards from the deck.
     * @param items The cards that he will have
     */
    public void drawCards(final ArrayList<Goods> items) {
        if (role == RoleType.Trader) {
            cards.addAll(items);
        }
    }

    /**
     * Creates a bag, based on the player strategy.
     */
    public void createBag() {
        if (role == RoleType.Trader) {
            strategy.createBag(cards);
        }
    }

    /**
     * The player will declare the goods stored on the bag.
     */
    public void declareGoods() {
        if (role == RoleType.Trader) {
            strategy.declareGoods();
        }
    }

    /**
     * If the player is a sheriff, inspect the other players bags.
     */
    public void inspect() {
        if (role == RoleType.Sheriff) {
            strategy.inspect();
        }
    }

    /**
     * Prepare a player for the next round.
     */
    public void prepareNextRound() {
        cards.clear();
        bag.clear();
    }


}

