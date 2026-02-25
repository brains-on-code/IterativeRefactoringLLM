package com.thealgorithms.sorts;

/**
 * Implementation of Counting Sort for non-negative integers.
 */
class CountingSort {

    /**
     * Sorts the given array of non-negative integers using Counting Sort.
     *
     * @param array the array to be sorted
     * @return the sorted array (same instance as input)
     */
    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMax(array);

        // Frequency array where index represents the value and element represents its count
        int[] counts = new int[maxValue + 1];

        // Count occurrences of each value
        for (int value : array) {
            counts[value]++;
        }

        // Overwrite original array with sorted values based on counts
        int index = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                array[index++] = value;
                counts[value]--;
            }
        }

        return array;
    }

    /**
     * Finds the maximum value in the given array.
     *
     * @param array the array to search
     * @return the maximum value found
     */
    private int findMax(Integer[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}