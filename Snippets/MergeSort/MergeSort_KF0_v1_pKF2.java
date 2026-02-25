package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Generic merge sort algorithm.
 *
 * @see SortAlgorithm
 */
class MergeSort implements SortAlgorithm {

    /** Auxiliary array used during merge operations. */
    private Comparable[] aux;

    /**
     * Sorts the given array using merge sort.
     *
     * @param unsorted the array to be sorted
     * @param <T>      the type of elements in the array, which must be Comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        doSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray arr[left..right] using merge sort.
     *
     * @param arr   the array to be sorted
     * @param left  the left index of the subarray (inclusive)
     * @param right the right index of the subarray (inclusive)
     */
    private <T extends Comparable<T>> void doSort(T[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) >>> 1;
            doSort(arr, left, mid);
            doSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    /**
     * Merges two consecutive sorted subarrays of arr into a single sorted segment.
     * The subarrays are:
     * - arr[left..mid]
     * - arr[mid + 1..right]
     *
     * @param arr   the array containing the subarrays to merge
     * @param left  the left index of the first subarray
     * @param mid   the ending index of the first subarray
     * @param right the ending index of the second subarray
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;

        System.arraycopy(arr, left, aux, left, right + 1 - left);

        for (int k = left; k <= right; k++) {
            if (j > right) {
                arr[k] = (T) aux[i++];
            } else if (i > mid) {
                arr[k] = (T) aux[j++];
            } else if (less(aux[j], aux[i])) {
                arr[k] = (T) aux[j++];
            } else {
                arr[k] = (T) aux[i++];
            }
        }
    }
}