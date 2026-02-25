package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * Dark Sort uses a counting approach to sort non-negative integers.
 */
class DarkSort {

    /**
     * Sorts the array using the Dark Sort algorithm.
     *
     * @param array the array to be sorted
     * @return the sorted array
     */
    public Integer[] sort(Integer[] array) {
        if (!isSortable(array)) {
            return array;
        }

        int maxValue = findMax(array);
        int[] counts = buildCounts(array, maxValue);

        return buildSortedArray(array.length, counts);
    }

    private boolean isSortable(Integer[] array) {
        return array != null && array.length > 1;
    }

    private int[] buildCounts(Integer[] array, int maxValue) {
        int[] counts = new int[maxValue + 1];
        for (int value : array) {
            counts[value]++;
        }
        return counts;
    }

    private Integer[] buildSortedArray(int length, int[] counts) {
        Integer[] sorted = new Integer[length];
        int index = 0;

        for (int value = 0; value < counts.length; value++) {
            int occurrences = counts[value];
            for (int i = 0; i < occurrences; i++) {
                sorted[index++] = value;
            }
        }

        return sorted;
    }

    /**
     * Finds the maximum value in an array.
     *
     * @param array the array
     * @return the maximum value
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