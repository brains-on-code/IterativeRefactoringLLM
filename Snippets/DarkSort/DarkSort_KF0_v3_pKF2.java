package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * <p>A counting-based sort for non-negative integers. It counts the
 * occurrences of each value and reconstructs the array in ascending order.
 */
class DarkSort {

    /**
     * Sorts the given array using Dark Sort.
     *
     * @param array the array to be sorted
     * @return the same array instance, sorted in ascending order
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
            int occurrences = counts[value];
            while (occurrences-- > 0) {
                array[index++] = value;
            }
        }

        return array;
    }

    /**
     * Finds the maximum value in the given array.
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