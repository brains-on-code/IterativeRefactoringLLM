package com.thealgorithms.sorts;

class DarkSort {

    public Integer[] sort(final Integer[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int maxValue = findMaxValue(array);
        final int[] counts = buildCountsArray(array, maxValue);
        fillSortedArray(array, counts);

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

    private int[] buildCountsArray(final Integer[] array, final int maxValue) {
        final int[] counts = new int[maxValue + 1];
        for (final int value : array) {
            counts[value]++;
        }
        return counts;
    }

    private void fillSortedArray(final Integer[] array, final int[] counts) {
        int index = 0;
        for (int value = 0; value < counts.length; value++) {
            final int count = counts[value];
            for (int i = 0; i < count; i++) {
                array[index++] = value;
            }
        }
    }
}