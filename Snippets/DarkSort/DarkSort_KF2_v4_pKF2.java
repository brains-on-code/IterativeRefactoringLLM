package com.thealgorithms.sorts;

/**
 * Counting sort implementation for non-negative integers.
 */
class DarkSort {

    /**
     * Sorts the given array of non-negative integers in ascending order using counting sort.
     *
     * @param unsorted the array to sort
     * @return the same array instance, sorted in-place; or the input if it is null or has length ≤ 1
     */
    public Integer[] sort(Integer[] unsorted) {
        if (unsorted == null || unsorted.length <= 1) {
            return unsorted;
        }

        int maxValue = findMax(unsorted);
        int[] counts = new int[maxValue + 1];

        // Count the occurrences of each value
        for (int value : unsorted) {
            counts[value]++;
        }

        // Overwrite the original array with sorted values
        int sortedIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            int frequency = counts[value];
            while (frequency-- > 0) {
                unsorted[sortedIndex++] = value;
            }
        }

        return unsorted;
    }

    /**
     * Returns the maximum value in the given non-empty array.
     *
     * @param arr the array to search
     * @return the maximum value in the array
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