package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * This is a counting-based sort that assumes all input values are
 * non-negative integers. It counts the occurrences of each value and
 * reconstructs the array in sorted order.
 */
class DarkSort {

    /**
     * Sorts the given array using Dark Sort.
     *
     * @param unsorted the array to be sorted
     * @return the sorted array (same instance as input)
     */
    public Integer[] sort(Integer[] unsorted) {
        if (unsorted == null || unsorted.length <= 1) {
            return unsorted;
        }

        int max = findMax(unsorted);
        int[] counts = new int[max + 1];

        for (int value : unsorted) {
            counts[value]++;
        }

        int index = 0;
        for (int value = 0; value < counts.length; value++) {
            int occurrences = counts[value];
            while (occurrences-- > 0) {
                unsorted[index++] = value;
            }
        }

        return unsorted;
    }

    /**
     * Returns the maximum value in the given array.
     *
     * @param arr the array to search
     * @return the maximum value found
     * @throws IllegalArgumentException if the array is null or empty
     */
    private int findMax(Integer[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int max = arr[0];
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}