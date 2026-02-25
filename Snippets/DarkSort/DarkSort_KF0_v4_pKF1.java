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
     * @param array the array to be sorted
     * @return sorted array
     */
    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxElement = findMaxElement(array);
        int[] frequency = new int[maxElement + 1];

        for (int element : array) {
            frequency[element]++;
        }

        int currentIndex = 0;
        for (int value = 0; value < frequency.length; value++) {
            while (frequency[value] > 0) {
                array[currentIndex++] = value;
                frequency[value]--;
            }
        }

        return array;
    }

    /**
     * Helper method to find the maximum value in an array.
     *
     * @param array the array
     * @return the maximum value
     */
    private int findMaxElement(Integer[] array) {
        int maxElement = array[0];
        for (int element : array) {
            if (element > maxElement) {
                maxElement = element;
            }
        }
        return maxElement;
    }
}