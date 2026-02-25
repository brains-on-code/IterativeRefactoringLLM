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
        int[] counts = new int[maxValue + 1];

        for (int value : array) {
            counts[value]++;
        }

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