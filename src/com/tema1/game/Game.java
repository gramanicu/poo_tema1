package com.tema1.game;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.goods.LegalGoods;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.main.GameInput;
import com.tema1.player.Player;
import com.tema1.strategy.StrategyType;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public final class Game {
    private static final int MAX_LEGAL_ID = 10;
    private static Game instance = null;
    private ArrayList<Player> playerList;
    private Queue<Goods> goodsList;
    private int rounds;
    private static int currentRound;
    private GoodsFactory assetCreator;
    private boolean canRun;

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Game() {
        currentRound = 0;
        playerList = new ArrayList<>();
        goodsList = new LinkedList<>();
        assetCreator = new GoodsFactory();
    }

    /**
     * Loads all the data to run the game.
     * @param data The GameInput data
     */
    public void load(final GameInput data) {
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
    public void run() {
        if (!canRun) {
            return;
        }

        while (currentRound < rounds) {
            currentRound = doRound(currentRound);
        }

        // When the game ends
        computeScores();
    }

    private void computeScores() {
        ArrayList<Goods> goods = new ArrayList<>();
        for (int i = 0; i < MAX_LEGAL_ID; i++) {
            goods.add(GoodsFactory.getInstance().getGoodsById(i));
        }

        // Compute king/queen bonus
        for (Goods goodChecked : goods) {
            // If this item is not legal, go to the next one
            if (goodChecked.getType() == GoodsType.Illegal) {
                continue;
            }

            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<Integer> frequencies = new ArrayList<>();

            for (Player player : playerList) {
                // Search through all players items
                for (Goods toCheck : player.getStallItems()) {
                    // Check if the items has the correct id
                    if (toCheck.getId() == goodChecked.getId()) {
                        if (!ids.contains(player.getId())) {
                            ids.add(player.getId());
                            frequencies.add(1);
                        } else {
                            int index = ids.indexOf(player.getId());
                            Integer value = frequencies.get(index);
                            value = value + 1;
                            frequencies.set(index, value);
                        }
                    }
                }
            }

            switch (ids.size()) {
                case 0:
                    // If no-one has this item, go to the next one.
                    continue;
                case 1:
                    // If only 1 player has the item, he is automatically the king
                     playerList.get(ids.get(0))
                             .addProfitBonus(((LegalGoods) goodChecked).getKingBonus());
                     continue;
                default:
                    break;
            }

            ArrayList<IDFrequencyPair> frequencyList = new ArrayList<>();
            // Combine the two arrays
            for (Integer id : ids) {
                frequencyList.add(new IDFrequencyPair(id, frequencies.get(ids.indexOf(id))));
            }

            // Sort the list
            IDFrequencyPairComparator comparator = new IDFrequencyPairComparator();
            frequencyList.sort(comparator);

            // Set the king/queen
            int kingId = frequencyList.get(0).getId();
            int queenId = frequencyList.get(1).getId();

            playerList.get(kingId).addProfitBonus(((LegalGoods) goodChecked).getKingBonus());
            playerList.get(queenId).addProfitBonus(((LegalGoods) goodChecked).getQueenBonus());
        }

        LeaderBoard.getInstance().addScores(playerList);
        LeaderBoard.getInstance().printLeaderBoard();
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

    private void shopSupplying() {
        for (Player p : playerList) {
            p.prepareNextRound();
        }
    }

    /**
     * Check if the game is in a odd round.
     * @return isOddRound
     */
    public boolean isOddRound() {
        // First round is odd, but in reality, it's not (0), so ...
        return (currentRound % 2) == 0;
    }

}
