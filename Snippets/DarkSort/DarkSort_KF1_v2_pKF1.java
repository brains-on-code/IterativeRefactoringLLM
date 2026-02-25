package com.thealgorithms.sorts;

class CountingSort {

    public Integer[] sort(Integer[] inputArray) {
        if (inputArray == null || inputArray.length <= 1) {
            return inputArray;
        }

        int maxValue = findMaxValue(inputArray);
        int[] valueCounts = new int[maxValue + 1];

        for (int value : inputArray) {
            valueCounts[value]++;
        }

        int sortedIndex = 0;
        for (int value = 0; value < valueCounts.length; value++) {
            while (valueCounts[value] > 0) {
                inputArray[sortedIndex++] = value;
                valueCounts[value]--;
            }
        }

        return inputArray;
    }

    private int findMaxValue(Integer[] inputArray) {
        int maxValue = inputArray[0];
        for (int value : inputArray) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}