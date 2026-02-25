package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts the specified range of the array using insertion sort.
     *
     * @param array the array to sort
     * @param left  the inclusive start index
     * @param right the exclusive end index
     * @param <T>   the element type
     * @return the sorted array
     */
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

    /**
     * Sorts the array using insertion sort after first moving the minimum
     * element to the front. This can reduce the number of boundary checks
     * in some insertion sort implementations.
     *
     * @param array the array to sort
     * @param <T>   the element type
     * @return the sorted array
     */
    public <T extends Comparable<T>> T[] sortWithMinToFront(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = indexOfMin(array);
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

    /**
     * Returns the index of the minimum element in the array.
     *
     * @param array the array to search
     * @param <T>   the element type
     * @return the index of the minimum element
     */
    private <T extends Comparable<T>> int indexOfMin(final T[] array) {
        int minIndex = 0;

        for (int i = 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }

        return minIndex;
    }
}