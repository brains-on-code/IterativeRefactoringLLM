package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Generic merge sort algorithm.
 *
 * @see SortAlgorithm
 */
class MergeSort implements SortAlgorithm {

    private Comparable<?>[] aux;

    /**
     * Sorts the given array using merge sort.
     *
     * @param unsorted the array to be sorted
     * @param <T>      the element type, which must be Comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        if (unsorted == null || unsorted.length < 2) {
            return unsorted;
        }

        aux = new Comparable[unsorted.length];
        mergeSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray from {@code left} to {@code right}.
     */
    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(array, left, middle);
        mergeSort(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    /**
     * Merges two sorted subarrays of {@code array}:
     * <ul>
     *   <li>from {@code left} to {@code middle}</li>
     *   <li>from {@code middle + 1} to {@code right}</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int middle, int right) {
        System.arraycopy(array, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = middle + 1;

        for (int current = left; current <= right; current++) {
            if (leftIndex > middle) {
                array[current] = (T) aux[rightIndex++];
            } else if (rightIndex > right) {
                array[current] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                array[current] = (T) aux[rightIndex++];
            } else {
                array[current] = (T) aux[leftIndex++];
            }
        }
    }
}