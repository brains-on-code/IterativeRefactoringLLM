package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        return sort(array, 0, array.length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int lo, final int hi) {
        if (array == null || lo >= hi) {
            return array;
        }

        for (int i = lo + 1; i < hi; i++) {
            final T key = array[i];
            int j = i - 1;

            while (j >= lo && SortUtils.less(key, array[j])) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T currentValue = array[i];
            int j = i;

            while (SortUtils.less(currentValue, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = currentValue;
        }

        return array;
    }

    private <T extends Comparable<T>> int findMinIndex(final T[] array) {
        int minIndex = 0;

        for (int i = 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }

        return minIndex;
    }
}