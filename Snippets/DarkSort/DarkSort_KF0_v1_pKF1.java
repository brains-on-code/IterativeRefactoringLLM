package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * Dark Sort uses a temporary array to count occurrences of elements and
 * reconstructs the sorted array based on the counts.
 */
class DarkSort {

    /**
     * Sorts the array using the Dark Sort algorithm.
     *
     * @param array the array to be sorted
     * @return sorted array
     */
    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMaxValue(array);

        int[] occurrenceCounts = new int[maxValue + 1];

        for (int value : array) {
            occurrenceCounts[value]++;
        }

        int sortedIndex = 0;
        for (int value = 0; value < occurrenceCounts.length; value++) {
            while (occurrenceCounts[value] > 0) {
                array[sortedIndex++] = value;
                occurrenceCounts[value]--;
            }
        }

        return array;
    }

    /**
     * Helper method to find the maximum value in an array.
     *
     * @param array the array
     * @return the maximum value
     */
    private int findMaxValue(Integer[] array) {
        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}