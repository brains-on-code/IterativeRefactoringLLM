package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation using a single reusable auxiliary array.
 */
class MergeSort implements SortAlgorithm {

    /** Auxiliary array reused across merge operations. */
    private Comparable<?>[] aux;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        sortRange(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray arr[left..right].
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
     * Merges two sorted subarrays of arr into a single sorted range:
     * - left subarray:  arr[left..mid]
     * - right subarray: arr[mid+1..right]
     * Result is stored in arr[left..right].
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        System.arraycopy(arr, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = mid + 1;

        for (int destIndex = left; destIndex <= right; destIndex++) {
            boolean leftExhausted = leftIndex > mid;
            boolean rightExhausted = rightIndex > right;

            if (leftExhausted) {
                arr[destIndex] = (T) aux[rightIndex++];
            } else if (rightExhausted) {
                arr[destIndex] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                arr[destIndex] = (T) aux[rightIndex++];
            } else {
                arr[destIndex] = (T) aux[leftIndex++];
            }
        }
    }
}