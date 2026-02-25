package com.thealgorithms.sorts;

/**
 * A simple implementation of counting sort for non-negative integers.
 */
class DarkSort {

    /**
     * Sorts the given array of non-negative integers in ascending order using counting sort.
     *
     * @param unsorted the array to sort
     * @return the sorted array (same reference as input), or the input if it is null or has length ≤ 1
     */
    public Integer[] sort(Integer[] unsorted) {
        if (unsorted == null || unsorted.length <= 1) {
            return unsorted;
        }

        int maxValue = findMax(unsorted);

        // Frequency array where index represents the value and the element at that index
        // represents how many times it appears in the input array.
        int[] counts = new int[maxValue + 1];

        // Count occurrences of each value.
        for (int value : unsorted) {
            counts[value]++;
        }

        // Overwrite the original array with values in sorted order.
        int sortedIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                unsorted[sortedIndex++] = value;
                counts[value]--;
            }
        }

        return unsorted;
    }

    /**
     * Finds the maximum value in the given non-empty array.
     *
     * @param arr the array to search
     * @return the maximum value found
     */
    private int findMax(Integer[] arr) {
        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}