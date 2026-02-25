package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (isTriviallySorted(array)) {
            return array;
        }
        return sort(array, 0, array.length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int lo, final int hi) {
        if (array == null || lo >= hi) {
            return array;
        }

        for (int i = lo + 1; i < hi; i++) {
            insertElement(array, lo, i);
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (isTriviallySorted(array)) {
            return array;
        }

        final int minIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            insertWithSentinel(array, i);
        }

        return array;
    }

    private <T extends Comparable<T>> void insertElement(T[] array, int lo, int index) {
        final T key = array[index];
        int j = index - 1;

        while (j >= lo && SortUtils.less(key, array[j])) {
            array[j + 1] = array[j];
            j--;
        }

        array[j + 1] = key;
    }

    private <T extends Comparable<T>> void insertWithSentinel(T[] array, int index) {
        final T key = array[index];
        int j = index;

        while (SortUtils.less(key, array[j - 1])) {
            array[j] = array[j - 1];
            j--;
        }

        array[j] = key;
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

    private <T> boolean isTriviallySorted(T[] array) {
        return array == null || array.length <= 1;
    }
}