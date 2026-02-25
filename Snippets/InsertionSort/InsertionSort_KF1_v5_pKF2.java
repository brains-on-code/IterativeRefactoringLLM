package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts a subrange of the given array using insertion sort.
     *
     * @param array the array to sort
     * @param left  inclusive start index of the range to sort
     * @param right exclusive end index of the range to sort
     * @param <T>   the element type
     * @return the array with the specified range sorted in-place
     */
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

    /**
     * Sorts the entire array using insertion sort, after first moving the
     * minimum element to the front.
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
            final T current = array[i];
            int j = i;

            while (j > 0 && SortUtils.less(current, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = current;
        }

        return array;
    }

    /**
     * Returns the index of the minimum element in the given array.
     *
     * @param array the array to search
     * @param <T>   the element type
     * @return index of the minimum element
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