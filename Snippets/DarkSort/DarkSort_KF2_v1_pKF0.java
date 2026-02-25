package com.thealgorithms.sorts;

class DarkSort {

    public Integer[] sort(Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int maxValue = findMaxValue(array);
        int[] counts = buildCountsArray(array, maxValue);
        fillSortedArray(array, counts);

        return array;
    }

    private int findMaxValue(Integer[] array) {
        int max = array[0];
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int[] buildCountsArray(Integer[] array, int maxValue) {
        int[] counts = new int[maxValue + 1];
        for (int value : array) {
            counts[value]++;
        }
        return counts;
    }

    private void fillSortedArray(Integer[] array, int[] counts) {
        int index = 0;
        for (int value = 0; value < counts.length; value++) {
            while (counts[value] > 0) {
                array[index++] = value;
                counts[value]--;
            }
        }
    }
}