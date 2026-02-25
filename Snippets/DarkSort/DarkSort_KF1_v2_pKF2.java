package com.thealgorithms.sorts;

/**
 * Counting Sort implementation for arrays of non-negative integers.
 */
class CountingSort {

    /**
     * Sorts the given array of non-negative integers using Counting Sort.
     *
     * @param array the array to be sorted
     * @return the same array instance, sorted in non-decreasing order
     */
    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMax(array);
        int[] counts = new int[maxValue + 1];

        for (int value : array) {
            counts[value]++;
        }

        int index = 0;
        for (int value = 0; value < counts.length; value++) {
            int frequency = counts[value];
            for (int i = 0; i < frequency; i++) {
                array[index++] = value;
            }
        }

        return array;
    }

    /**
     * Returns the maximum value in the given non-empty array.
     *
     * @param array the array to search
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