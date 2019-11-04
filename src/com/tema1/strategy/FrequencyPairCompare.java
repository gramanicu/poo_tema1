package com.tema1.strategy;

import java.util.Comparator;

class FrequencyPairCompare implements Comparator<FrequencyPair> {
    /**
     * A method used to sort the FrequencyPair list by the frequency.
     * @param o1 first FrequencyPair
     * @param o2 second FrequencyPair
     * @return the difference between the second frequency and the first one
     */
    @Override
    public int compare(final FrequencyPair o1, final FrequencyPair o2) {
        return o2.getFrequency() - o1.getFrequency();
    }
}
