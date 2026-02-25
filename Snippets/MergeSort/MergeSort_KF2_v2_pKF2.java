package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation using a single reusable auxiliary array.
 */
class MergeSort implements SortAlgorithm {

    /** Auxiliary array used during merge operations. */
    private Comparable<?>[] aux;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        doSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray {@code arr[left..right]}.
     */
    private <T extends Comparable<T>> void doSort(T[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = (left + right) >>> 1;
        doSort(arr, left, mid);
        doSort(arr, mid + 1, right);
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

        int i = left;     // Index in left half of aux
        int j = mid + 1;  // Index in right half of aux

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                arr[k] = (T) aux[j++];
            } else if (j > right) {
                arr[k] = (T) aux[i++];
            } else if (less(aux[j], aux[i])) {
                arr[k] = (T) aux[j++];
            } else {
                arr[k] = (T) aux[i++];
            }
        }
    }
}