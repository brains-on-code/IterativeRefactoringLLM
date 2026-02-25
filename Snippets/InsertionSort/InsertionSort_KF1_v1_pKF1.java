package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int left, final int right) {
        if (array == null || left >= right) {
            return array;
        }

        for (int i = left + 1; i < right; i++) {
            final T key = array[i];
            int j = i - 1;
            while (j >= left && SortUtils.less(key, array[j])) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sortWithSentinel(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = indexOfMinimum(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T key = array[i];
            int j = i;
            while (j > 0 && SortUtils.less(key, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = key;
        }

        return array;
    }

    private <T extends Comparable<T>> int indexOfMinimum(final T[] array) {
        int minIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}