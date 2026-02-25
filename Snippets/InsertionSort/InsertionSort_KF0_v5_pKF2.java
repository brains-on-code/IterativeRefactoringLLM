package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts the subarray in the range [lo, hi) using insertion sort.
     *
     * @param array the array to sort
     * @param lo    the inclusive lower bound of the range to sort
     * @param hi    the exclusive upper bound of the range to sort
     * @param <T>   the element type, which must be comparable
     * @return the sorted array (same instance as the input)
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
     * The smallest element is first moved to index {@code 0} (the sentinel),
     * which allows the inner loop to omit explicit lower-bound checks.
     *
     * @param array the array to sort
     * @param <T>   the element type, which must be comparable
     * @return the sorted array (same instance as the input)
     */
    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T valueToInsert = array[i];
            int j = i;

            while (SortUtils.less(valueToInsert, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = valueToInsert;
        }

        return array;
    }

    /**
     * Finds the index of the smallest element in the given array.
     *
     * @param array the array to scan
     * @param <T>   the element type, which must be comparable
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