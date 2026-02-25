package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * <p>This is a counting-based sort that assumes all input values are
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

        int maxValue = findMax(unsorted);
        int[] valueCounts = new int[maxValue + 1];

        for (int value : unsorted) {
            valueCounts[value]++;
        }

        int sortedIndex = 0;
        for (int value = 0; value < valueCounts.length; value++) {
            int occurrences = valueCounts[value];
            while (occurrences-- > 0) {
                unsorted[sortedIndex++] = value;
            }
        }

        return unsorted;
    }

    /**
     * Returns the maximum value in the given array.
     *
     * @param array the array to search
     * @return the maximum value found
     * @throws IllegalArgumentException if the array is null or empty
     */
    private int findMax(Integer[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }

        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}