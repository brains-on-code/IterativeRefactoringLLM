package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation using a single reusable auxiliary array.
 */
class MergeSort implements SortAlgorithm {

    /** Shared auxiliary array used during merge operations. */
    private Comparable<?>[] aux;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        sortRange(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray {@code arr[left..right]}.
     */
    private <T extends Comparable<T>> void sortRange(T[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) >>> 1;

        sortRange(arr, left, mid);
        sortRange(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    /**
     * Merges two consecutive sorted subarrays of {@code arr}:
     * {@code arr[left..mid]} and {@code arr[mid+1..right]} into
     * {@code arr[left..right]}.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        System.arraycopy(arr, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = mid + 1;

        for (int destIndex = left; destIndex <= right; destIndex++) {
            if (leftIndex > mid) {
                arr[destIndex] = (T) aux[rightIndex++];
            } else if (rightIndex > right) {
                arr[destIndex] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                arr[destIndex] = (T) aux[rightIndex++];
            } else {
                arr[destIndex] = (T) aux[leftIndex++];
            }
        }
    }
}