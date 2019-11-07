package com.tema1.game;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Score> {
    /**
     * Compares two scores based on the points and id.
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(final Score o1, final Score o2) {
        int diff = o2.getPoints() - o1.getPoints();

        if (diff != 0) {
            return diff;
        }

        return o1.getId() - o2.getId();
    }
}
