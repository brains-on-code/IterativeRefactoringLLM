package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Generic merge sort algorithm.
 *
 * @see SortAlgorithm
 */
class MergeSort implements SortAlgorithm {

    private Comparable[] tempArray;

    /**
     * Generic merge sort algorithm implementation.
     *
     * @param array the array which should be sorted.
     * @param <T> Comparable class.
     * @return sorted array.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        tempArray = new Comparable[array.length];
        sortRange(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively sorts the specified range of the array.
     *
     * @param array the array to be sorted.
     * @param left the first index of the range.
     * @param right the last index of the range.
     */
    private <T extends Comparable<T>> void sortRange(T[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) >>> 1;
            sortRange(array, left, middle);
            sortRange(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    /**
     * Merges two sorted subranges of the array.
     *
     * @param array the array containing the subranges to be merged.
     * @param left the first index of the left subrange.
     * @param middle the last index of the left subrange (middle point).
     * @param right the last index of the right subrange.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int middle, int right) {
        int leftIndex = left;
        int rightIndex = middle + 1;

        System.arraycopy(array, left, tempArray, left, right + 1 - left);

        for (int current = left; current <= right; current++) {
            if (rightIndex > right) {
                array[current] = (T) tempArray[leftIndex++];
            } else if (leftIndex > middle) {
                array[current] = (T) tempArray[rightIndex++];
            } else if (less(tempArray[rightIndex], tempArray[leftIndex])) {
                array[current] = (T) tempArray[rightIndex++];
            } else {
                array[current] = (T) tempArray[leftIndex++];
            }
        }
    }
}