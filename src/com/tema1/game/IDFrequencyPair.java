package com.tema1.game;

/*
    This class is used to determine the king/queen bonuses.
    For each good category, we will have a list of players and
    how many goods of that type they have.
 */
public class IDFrequencyPair {
    private int id;
    private int frequency;

    public IDFrequencyPair(final int id, final int frequency) {
        this.id = id;
        this.frequency = frequency;
    }

    /**
     * @return The id of the player
     */
    public int getId() {
        return id;
    }

    /**
     * @return How many goods he has
     */
    public int getFrequency() {
        return frequency;
    }
}
