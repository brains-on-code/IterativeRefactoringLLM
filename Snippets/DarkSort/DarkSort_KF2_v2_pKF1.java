package com.thealgorithms.sorts;

class CountingSort {

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