package com.tema1.game;

import com.tema1.player.Player;

import java.util.ArrayList;

public final class LeaderBoard {
    private static LeaderBoard instance = null;
    private ArrayList<Score> table;
    private ArrayList<Player> players;

    public static LeaderBoard getInstance() {
        if (instance == null) {
            instance = new LeaderBoard();
        }
        return instance;
    }

    private LeaderBoard() {
        players = new ArrayList<>();
        table = new ArrayList<>();
    }

    private void prepareTable() {
        for (Player player : players) {
            table.add(new Score(player.getId(), player.getStrategy(), player.computeProfits()));
        }
    }

    /**
     * Adds all the players to the leaderBoard.
     * @param playerList The list of players in the game, at the end
     */
    public void addScores(final ArrayList<Player> playerList) {
        this.players.addAll(playerList);
        prepareTable();
    }

    private void processTable() {
        ScoreComparator comparator = new ScoreComparator();
        table.sort(comparator);
    }

    /**
     * Prints the LeaderBoard.
     */
    public void printLeaderBoard() {
        processTable();

        for (Score s : table) {
            System.out.println(s.getScore());
        }
    }
}
