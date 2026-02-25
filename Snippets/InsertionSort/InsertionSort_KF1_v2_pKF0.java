package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array != null ? array.length : 0);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int left, final int right) {
        if (array == null || left >= right) {
            return array;
        }

        for (int i = left + 1; i < right; i++) {
            final T currentElement = array[i];
            int j = i - 1;

            while (j >= left && SortUtils.less(currentElement, array[j])) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = currentElement;
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
            final T currentElement = array[i];
            int j = i;

            while (SortUtils.less(currentElement, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = currentElement;
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