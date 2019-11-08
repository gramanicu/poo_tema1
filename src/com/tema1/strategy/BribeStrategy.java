package com.tema1.strategy;

import com.tema1.goods.Goods;
import com.tema1.goods.GoodsFactory;
import com.tema1.goods.GoodsType;
import com.tema1.helpers.Constants;
import com.tema1.helpers.RoleType;
import com.tema1.player.Bag;
import com.tema1.player.Player;

import java.util.ArrayList;
import java.util.Queue;

public class BribeStrategy extends BaseStrategy {
    /**
     * Creates a new bag, based on the players strategy.
     * @param cards The items that the player has available
     * @return The bag with the selected items
     */
    public Bag createBag(final ArrayList<Goods> cards, final int money) {
        Bag bag = new Bag();
        ArrayList<FrequencyPair> listToFilter = sortedFrequencyList(cards);
        ArrayList<Goods> items = new ArrayList<>();

        for (FrequencyPair pair : listToFilter) {
            for (int i = 0; i < pair.getFrequency(); i++) {
                items.add(pair.getItem());
            }
        }

        ArrayList<Goods> finalList = new ArrayList<>();

        // Check if any illegal items were found.
        if (illegalCount(items) == 0) {
            // If there are only legal ones, play base strategy
            FrequencyPair legalItem = listToFilter.get(0);
            for (int i = 0; i < legalItem.getFrequency(); i++) {
                finalList.add(legalItem.getItem());
            }
            bag.setItems(finalList);
            bag.setBribe(0);
            bag.declareItems(legalItem.getItem());
            return bag;
        }

        // If there are illegal items, check that he has money for penalties
        if (money < Constants.FEW_ILLEGALS_BRIBE + Constants.ILLEGAL_FINE) {
            // Play base strategy if he doesn't have enough money
            ArrayList<FrequencyPair> legalItems = new ArrayList<>();

            // Filter out illegal goods
            for (FrequencyPair pair : listToFilter) {
                if (pair.getItem().getType() == GoodsType.Legal) {
                    legalItems.add(pair);
                }
            }

            // Check if there are legal goods
            if (legalItems.size() == 0) {
                // Choose the best illegal good
                FrequencyPair itemToAdd = listToFilter.get(0);
                bag.addItem(itemToAdd.getItem());

                // Declare it as apples
                bag.setBribe(0);
                bag.declareItems(GoodsFactory.getInstance().getGoodsById(0));
                return bag;
            } else {
                bag.addItem(legalItems.get(0).getItem(), legalItems.get(0).getFrequency());
                bag.setBribe(0);
                bag.declareItems(legalItems.get(0).getItem());
                return bag;
            }
        }

        int penalties = 0;
        int illegalCount = 0;

        items = new ArrayList<>(cards);
        ItemValueComparator comparator = new ItemValueComparator();
        items.sort(comparator);

        for (Goods item : items) {
            if (finalList.size() >= Constants.MAX_BAG_SIZE) {
                break;
            }

            int penalty = item.getPenalty();


            if (penalties + penalty < money) {
                if (item.getType() == GoodsType.Illegal) {
                    illegalCount++;
                }
                finalList.add(item);
                penalties += item.getPenalty();
            }
        }

        for (Goods item : finalList) {
            bag.addItem(item);
        }

        if (illegalCount > 2) {
            bag.setBribe(Constants.MANY_ILLEGALS_BRIBE);
        } else {
            bag.setBribe(Constants.FEW_ILLEGALS_BRIBE);
        }

        bag.declareItems(GoodsFactory.getInstance().getGoodsById(0));

        return bag;
    }

    /**
     * Computes the number of illegal items in an ArrayList of Goods.
     * @param list The ArrayList
     * @return The number of illegal items
     */
    private int illegalCount(final ArrayList<Goods> list) {
        int illegals = 0;

        for (Goods item : list) {
            if (item.getType() == GoodsType.Illegal) {
                illegals++;
            }
        }

        return illegals;
    }

    /**
     * The sheriff will inspect the bags of the other players.
     * @param players   The list of players in the game
     * @param cardsDeck The current deck of cards on the "table"
     */
    @Override
    public void inspect(final ArrayList<Player> players, final Queue<Goods> cardsDeck) {
        Player sheriff = null;
        int sheriffID = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getRole() == RoleType.Sheriff) {
                sheriffID = i;
                sheriff = players.get(i);
                break;
            }
        }

        if (sheriffID == -1) {
            return;
        }

        // Inspect Left Player
        int leftID = sheriffID - 1;
        if (leftID == -1) {
            leftID = players.size() - 1;
        }
        Player player = players.get(leftID);
        Bag bag = player.getBag();
        if (sheriff.getMoney() >= Constants.MIN_MONEY_FOR_INSPECTION) {
            sheriff.setMoney(bag.inspect(sheriff.getMoney(), cardsDeck));
        }

        // If there are only two players, the left and the right one are the same
        int rightID = sheriffID + 1;
        if (rightID == players.size()) {
            rightID = 0;
        }
        if (players.size() > 2) {
            // Inspect Right Player
            player = players.get(rightID);
            bag = player.getBag();
            if (sheriff.getMoney() >= Constants.MIN_MONEY_FOR_INSPECTION) {
                sheriff.setMoney(bag.inspect(sheriff.getMoney(), cardsDeck));
            }
        }

        for (int id = 0; id < players.size(); id++) {
            if (id == sheriffID || id == leftID || id == rightID) {
                continue;
            }
            player = players.get(id);
            bag = player.getBag();
            sheriff.setMoney(sheriff.getMoney() + bag.takeBribe());
        }
    }
}
