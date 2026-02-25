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

        int maxElement = findMaxElement(inputArray);

        int[] frequencyArray = new int[maxElement + 1];

        for (int element : inputArray) {
            frequencyArray[element]++;
        }

        int currentIndex = 0;
        for (int value = 0; value < frequencyArray.length; value++) {
            while (frequencyArray[value] > 0) {
                inputArray[currentIndex++] = value;
                frequencyArray[value]--;
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