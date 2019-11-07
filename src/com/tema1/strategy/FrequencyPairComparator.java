package com.tema1.strategy;

import java.util.Comparator;

class FrequencyPairComparator implements Comparator<FrequencyPair> {
    /**
     * A method used to sort the FrequencyPair list by the frequency.
     * @param o1 first FrequencyPair
     * @param o2 second FrequencyPair
     * @return the difference between the second frequency and the first one
     */
    @Override
    public int compare(final FrequencyPair o1, final FrequencyPair o2) {
        int diff =  o2.getFrequency() - o1.getFrequency();
        if (diff != 0) {
            return diff;
        }

        diff = o2.getItem().getProfit() - o1.getItem().getProfit();
        if (diff != 0) {
            return diff;
        }

        diff = o2.getItem().getId() - o1.getItem().getId();
        return diff;
    }
}
