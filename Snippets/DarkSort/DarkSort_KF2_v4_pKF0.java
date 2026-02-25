package com.thealgorithms.sorts;

class DarkSort {

    public Integer[] sort(final Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int maxValue = findMaxValue(array);
        final int[] counts = countOccurrences(array, maxValue);
        overwriteWithSortedValues(array, counts);

        return array;
    }

    private int findMaxValue(final Integer[] array) {
        int max = array[0];
        for (final int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int[] countOccurrences(final Integer[] array, final int maxValue) {
        final int[] counts = new int[maxValue + 1];
        for (final int value : array) {
            counts[value]++;
        }
        return counts;
    }

    private void overwriteWithSortedValues(final Integer[] array, final int[] counts) {
        int arrayIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            for (int occurrence = 0; occurrence < counts[value]; occurrence++) {
                array[arrayIndex++] = value;
            }
        }
    }
}