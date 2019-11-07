package com.tema1.game;

import java.util.Comparator;

public class IDFrequencyPairComparator implements Comparator<IDFrequencyPair> {
    /**
     * A method used to sort the IDFrequencyPair list by the frequency.
     * @param o1 The first IDFrequencyPair
     * @param o2 The second IDFrequencyPair
     * @return The difference between the second frequency and the first
     */
    @Override
    public int compare(final IDFrequencyPair o1, final IDFrequencyPair o2) {
        int diff = o2.getFrequency() - o1.getFrequency();
        if (diff != 0) {
            return diff;
        }

        // If they have the same amount of items, arrange them in the initial order
        return o1.getId() - o2.getId();

    }
}
