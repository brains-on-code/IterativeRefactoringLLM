package com.thealgorithms.sorts;

class CountingSort {

    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMaxValue(array);
        int[] counts = new int[maxValue + 1];

        for (int value : array) {
            counts[value]++;
        }

        int sortedIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                array[sortedIndex++] = value;
                counts[value]--;
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