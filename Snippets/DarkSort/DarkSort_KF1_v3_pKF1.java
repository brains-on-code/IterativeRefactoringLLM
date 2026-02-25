package com.thealgorithms.sorts;

class CountingSort {

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