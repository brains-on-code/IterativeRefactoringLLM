package com.thealgorithms.sorts;

/**
 * Dark Sort algorithm implementation.
 *
 * Dark Sort uses a temporary array to count occurrences of elements and
 * reconstructs the sorted array based on the counts.
 */
class DarkSort {

    /**
     * Sorts the array using the Dark Sort algorithm.
     *
     * @param inputArray the array to be sorted
     * @return sorted array
     */
    public Integer[] sort(Integer[] inputArray) {
        if (inputArray == null || inputArray.length <= 1) {
            return inputArray;
        }

        int maxValue = findMaxValue(inputArray);
        int[] counts = new int[maxValue + 1];

        for (int value : inputArray) {
            counts[value]++;
        }

        int writeIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                inputArray[writeIndex++] = value;
                counts[value]--;
            }
        }

        return inputArray;
    }

    /**
     * Helper method to find the maximum value in an array.
     *
     * @param array the array
     * @return the maximum value
     */
    private int findMaxValue(Integer[] array) {
        int maxValue = array[0];
        for (int value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}