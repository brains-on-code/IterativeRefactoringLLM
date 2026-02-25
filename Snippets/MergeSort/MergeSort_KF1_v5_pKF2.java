package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    /** Temporary buffer used during merging. */
    private Comparable[] aux;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        aux = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively sorts the subarray {@code array[left..right]}.
     */
    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) >>> 1;

        mergeSort(array, left, mid);
        mergeSort(array, mid + 1, right);
        merge(array, left, mid, right);
    }

    /**
     * Merges two consecutive sorted subarrays of {@code array}:
     * {@code array[left..mid]} and {@code array[mid + 1..right]}.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right) {
        System.arraycopy(array, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = mid + 1;

        for (int current = left; current <= right; current++) {
            boolean leftExhausted = leftIndex > mid;
            boolean rightExhausted = rightIndex > right;

            if (leftExhausted) {
                array[current] = (T) aux[rightIndex++];
            } else if (rightExhausted) {
                array[current] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                array[current] = (T) aux[rightIndex++];
            } else {
                array[current] = (T) aux[leftIndex++];
            }
        }
    }
}