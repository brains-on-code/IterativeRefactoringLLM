package com.thealgorithms.sorts;

class CountingSort {

    public Integer[] sort(Integer[] inputArray) {
        if (inputArray == null || inputArray.length <= 1) {
            return inputArray;
        }

        int maxValue = findMaxValue(inputArray);
        int[] counts = new int[maxValue + 1];

        for (int value : inputArray) {
            counts[value]++;
        }

        int sortedIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                inputArray[sortedIndex++] = value;
                counts[value]--;
            }
        }

        return inputArray;
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