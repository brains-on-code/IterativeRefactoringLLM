package com.thealgorithms.sorts;

/**
 * Insertion sort implementation.
 *
 * <p>Provides:
 * <ul>
 *   <li>Standard insertion sort on a full array or subarray</li>
 *   <li>Sentinel-based insertion sort optimization</li>
 * </ul>
 */
class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts a subarray using insertion sort.
     *
     * @param array the array containing the range to sort
     * @param lo    inclusive lower bound of the range
     * @param hi    exclusive upper bound of the range
     * @param <T>   element type
     * @return the same array instance, with array[lo..hi) sorted
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
     *
     * <p>The smallest element is first moved to index {@code 0} (the sentinel),
     * which allows the inner loop to omit explicit lower-bound checks.</p>
     *
     * @param array the array to sort
     * @param <T>   element type
     * @return the same array instance, sorted
     */
    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = findMinIndex(array);
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

    /**
     * Returns the index of the minimum element in the array.
     *
     * @param array the array to scan
     * @param <T>   element type
     * @return index of the minimum element
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