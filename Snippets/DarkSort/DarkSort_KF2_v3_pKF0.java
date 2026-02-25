package com.thealgorithms.sorts;

class DarkSort {

    public Integer[] sort(final Integer[] array) {
        if (isTriviallySorted(array)) {
            return array;
        }

        final int maxValue = findMaxValue(array);
        final int[] counts = buildCountsArray(array, maxValue);
        writeSortedValues(array, counts);

        return array;
    }

    private boolean isTriviallySorted(final Integer[] array) {
        return array == null || array.length <= 1;
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

    private void writeSortedValues(final Integer[] array, final int[] counts) {
        int arrayIndex = 0;
        for (int value = 0; value < counts.length; value++) {
            int occurrences = counts[value];
            while (occurrences-- > 0) {
                array[arrayIndex++] = value;
            }
        }
    }
}