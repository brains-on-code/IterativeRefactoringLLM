package com.thealgorithms.sorts;

class CountingSort {

    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMaxValue(array);
        int[] frequency = new int[maxValue + 1];

        for (int value : array) {
            frequency[value]++;
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