package com.tema1.game;

import com.tema1.helpers.StrategyType;

public class Score {
    private int id;
    private StrategyType strategy;
    private int points;

    Score() {
        id = 0;
        strategy = StrategyType.Base;
        points = 0;
    }

    Score(final int id, final StrategyType strategy, final int score) {
        this.id = id;
        this.strategy = strategy;
        this.points = score;
    }

    /**
     * Set the id of the player.
     * @param id The id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * @return The id of the player.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Set the strategy of the player.
     * @param strategy The strategy
     */
    public void setStrategy(final StrategyType strategy) {
        this.strategy = strategy;
    }

    /**
     * Set the number of points the player has.
     * @param points The score
     */
    public void setPoints(final int points) {
        this.points = points;
    }

    /**
     * @return The number of points the player has.
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Print the score of the player.
     * @return A string with "ID Strategy Score"
     */
    public String getScore() {
        return String.format("%d %s %d", id, strategy.toString(), points);
    }
}
