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
import java.util.Queue;

public class Player {
    private int money;
    private static int playerCount = 0;
    private Strategy strategy;
    private RoleType role;
    private ArrayList<Goods> cards;
    private Stall stall;
    private Bag bag;
    private int id;
    private StrategyType strategyType;
    private int profits;


    private Player() {
        money = Constants.START_MONEY;
        cards = new ArrayList<>();
        bag = new Bag();
        stall = new Stall();
        id = playerCount;
        playerCount++;
        profits = -1;
    }

    public Player(final StrategyType strategy) {
        this();
        strategyType = strategy;
        setStrategy(strategy);
    }

    /**
     * @return The id of the player
     */
    public int getId() {
        return id;
    }

    /**
     * @return The strategy the player adopted
     */
    public StrategyType getStrategy() {
        return strategyType;
    }

    /**
     * @return Current money the player has
     */
    public int getMoney() {
        return money;
    }

    /**
     * Changes how much money the player has.
     * @param money The new amount of money
     */
    public void setMoney(final int money) {
        this.money = money;
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
     * @return The items the player has brought to his "shop"
     */
    public ArrayList<Goods> getStallItems() {
        return stall.getItems();
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
            bag = strategy.createBag(cards);
            cards.clear();
        }
    }

    /**
     * Returns the current player bag (is intended to be by reference, not value).
     * @return The players bag
     */
    public Bag getBag() {
        return this.bag;
    }

    /**
     * If the player is a sheriff, inspect the other players bags.
     * @param players The current list of players in the game
     * @param cardsDeck The current deck of cards on the "table"
     */
    public void inspect(final ArrayList<Player> players, final Queue<Goods> cardsDeck) {
        if (role == RoleType.Sheriff) {
            strategy.inspect(players, cardsDeck);
        }
    }

    /**
     * Prepare a player for the next round.
     */
    public void prepareNextRound() {
        if (role == RoleType.Trader) {
            stall.addItems(bag.getItems());
            money += bag.getBribe();
            bag.clear();
        }
    }

    private void computeProfits() {
        profits = money + stall.getScore();
    }

    /**
     * Add bonus for king/queen.
     * @param bonus The bonus value
     */
    public void addProfitBonus(final int bonus) {
        if (profits == -1) {
            computeProfits();
        }

        profits += bonus;
    }

    /**
     * Return the score the player has at the end.
     * @return The score
     */
    public int getProfit() {
        if (profits == -1) {
            computeProfits();
        }
        return profits;
    }

}

