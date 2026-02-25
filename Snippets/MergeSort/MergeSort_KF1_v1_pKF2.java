package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    /** Temporary array used for merging. */
    private Comparable[] aux;

    /**
     * Sorts the given array using merge sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        aux = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    /**
     * Recursively divides the array and sorts each half.
     *
     * @param array the array to sort
     * @param left  the left index of the subarray
     * @param right the right index of the subarray
     * @param <T>   the type of elements in the array
     */
    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) >>> 1;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays:
     * - first subarray:  array[left..mid]
     * - second subarray: array[mid+1..right]
     *
     * @param array the array containing the subarrays to merge
     * @param left  the left index of the first subarray
     * @param mid   the ending index of the first subarray
     * @param right the right index of the second subarray
     * @param <T>   the type of elements in the array
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;

        System.arraycopy(array, left, aux, left, right + 1 - left);

        for (int k = left; k <= right; k++) {
            if (j > right) {
                array[k] = (T) aux[i++];
            } else if (i > mid) {
                array[k] = (T) aux[j++];
            } else if (less(aux[j], aux[i])) {
                array[k] = (T) aux[j++];
            } else {
                array[k] = (T) aux[i++];
            }
        }
    }
}