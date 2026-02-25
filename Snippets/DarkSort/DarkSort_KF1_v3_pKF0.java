package com.thealgorithms.sorts;

/**
 * Counting sort implementation for non-negative integers.
 */
class CountingSort {

    /**
     * Sorts the given array of non-negative integers using counting sort.
     *
     * @param array the array to sort
     * @return the sorted array (same instance as input)
     */
    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMaxValue(array);
        int[] counts = buildFrequencyArray(array, maxValue);
        writeSortedValues(array, counts);

        return array;
    }

    /**
     * Builds a frequency array for the given input.
     *
     * @param array    the input array
     * @param maxValue the maximum value in the array
     * @return the frequency array
     */
    private int[] buildFrequencyArray(Integer[] array, int maxValue) {
        int[] counts = new int[maxValue + 1];
        for (int value : array) {
            counts[value]++;
        }
        return counts;
    }

    /**
     * Writes sorted values back into the original array based on the frequency array.
     *
     * @param array  the array to fill
     * @param counts the frequency array
     */
    private void writeSortedValues(Integer[] array, int[] counts) {
        int arrayIndex = 0;

        for (int value = 0; value < counts.length; value++) {
            int frequency = counts[value];

            for (int occurrence = 0; occurrence < frequency; occurrence++) {
                array[arrayIndex] = value;
                arrayIndex++;
            }
        }
    }

    /**
     * Finds the maximum value in the given array.
     *
     * @param array the array to search
     * @return the maximum value found
     */
    private int findMaxValue(Integer[] array) {
        int max = array[0];

        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }

        return max;
    }
}