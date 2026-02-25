package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    /**
     * Sorts the entire array using the insertion sort algorithm.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts a subarray using the insertion sort algorithm.
     *
     * @param array the array containing the subarray to be sorted
     * @param lo    the starting index of the subarray (inclusive)
     * @param hi    the ending index of the subarray (exclusive)
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the array with the specified subarray sorted
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
     * Sorts the array using insertion sort with a sentinel.
     *
     * <p>The algorithm first finds the minimum element and places it at index 0.
     * This sentinel allows the inner loop to omit the boundary check on each iteration,
     * since the minimum element guarantees termination.</p>
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
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

    /**
     * Returns the index of the minimum element in the array.
     *
     * @param array the array to be searched
     * @param <T>   the type of elements in the array, which must be comparable
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