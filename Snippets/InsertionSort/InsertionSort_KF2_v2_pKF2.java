package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts the subarray array[lo..hi) using insertion sort.
     *
     * @param array the array to sort
     * @param lo    the inclusive lower bound of the range to sort
     * @param hi    the exclusive upper bound of the range to sort
     * @param <T>   the element type
     * @return the sorted array (same instance as input)
     */
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

    /**
     * Sorts the entire array using insertion sort with a sentinel.
     * <p>
     * The smallest element is first moved to index 0 (the sentinel),
     * which allows the inner loop to omit explicit lower-bound checks.
     *
     * @param array the array to sort
     * @param <T>   the element type
     * @return the sorted array (same instance as input)
     */
    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minElemIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minElemIndex);

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

    /**
     * Finds the index of the minimum element in the given array.
     *
     * @param array the array to scan
     * @param <T>   the element type
     * @return the index of the minimum element
     */
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