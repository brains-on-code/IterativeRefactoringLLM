package com.thealgorithms.sorts;

/**
 * Counting sort for non-negative integers.
 *
 * <p>Counts occurrences of each value and reconstructs the array in ascending order.
 */
class DarkSort {

    /**
     * Sorts the given array in ascending order using counting sort.
     *
     * @param array the array to be sorted; must contain only non-negative integers
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
     * @param array the array to search; must not be null or empty
     * @return the maximum value found in the array
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