package com.tema1.main;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.player.Player;
import com.tema1.strategy.StrategyType;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// This must be unique
final class Game {
    private ArrayList<Player> playerList;
    private Queue<Goods> goodsList;
    private int rounds;
    private int currentRound;
    private GoodsFactory assetCreator;
    private boolean canRun;

    Game() {
        currentRound = 0;
        playerList = new ArrayList<>();
        goodsList = new LinkedList<>();
        assetCreator = new GoodsFactory();
    }

    /**
     * Loads all the data to run the game.
     * @param data The GameInput data
     */
    void load(final GameInput data) {
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
                default:
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

    /**
     * Starts the game.
     */
    void run() {
        if (!canRun) {
            return;
        }

        while (currentRound < rounds) {
            currentRound = doRound(currentRound);
        }
    }

    /**
     * Round of a game.
     * @param round The current round of the game
     * @return Incremented round
     */
    private int doRound(final int round) {
        int phase = 0;
        while (phase < playerList.size()) {
            phase = subRound(phase);
        }

        return round + 1;
    }

    /**
     * Sub-round of a game.
     * @param phase The current subRound of the round
     * @return Incremented subRound
     */
    private int subRound(final int phase) {
        roleAssignment(phase);
        bagCreation();
        inspection();
        shopSupplying();
        return phase + 1;
    }

    /**
     * Assign the sheriff role to the specified player.
     * @param sheriffIndex The player that will become a sheriff
     */
    private void roleAssignment(final int sheriffIndex) {
        for (Player player : playerList) {
            player.becomeTrader();
        }
        playerList.get(sheriffIndex).becomeSheriff();
    }

    private void bagCreation() {
        for (Player p : playerList) {
            /* Prepare the 10 cards for the player
               Because we remove elements, we must assure we can give the player the
               removed elements (that he is not a sheriff)
            */
            if (p.getRole() == RoleType.Trader) {
                ArrayList<Goods> cards = new ArrayList<>();
                for (int i = 0; i < Constants.CARDS_EACH_ROUND; i++) {
                    cards.add(goodsList.remove());
                }
                p.drawCards(cards);
                p.createBag();
            }
        }
    }

    private void inspection() {
        for (Player p : playerList) {
            p.inspect(playerList, goodsList);
        }
    }

    private void shopSupplying() { }

}
