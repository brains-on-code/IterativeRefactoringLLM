package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        int length = array == null ? 0 : array.length;
        return sort(array, 0, length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int left, final int right) {
        if (array == null || left >= right) {
            return array;
        }

        for (int i = left + 1; i < right; i++) {
            final T current = array[i];
            int j = i - 1;

            while (j >= left && SortUtils.less(current, array[j])) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = current;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sortWithSentinel(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        int minIndex = indexOfMinimum(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T current = array[i];
            int j = i;

            while (SortUtils.less(current, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = current;
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