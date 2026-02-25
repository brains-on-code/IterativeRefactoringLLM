package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    /** Temporary array used during merging. */
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
     * Recursively sorts the subarray {@code array[left..right]}.
     *
     * @param array the array to sort
     * @param left  left boundary (inclusive)
     * @param right right boundary (inclusive)
     * @param <T>   the type of elements in the array
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
     * <pre>
     *   left subarray  = array[left..mid]
     *   right subarray = array[mid + 1..right]
     * </pre>
     *
     * @param array the array containing the subarrays to merge
     * @param left  left boundary of the first subarray (inclusive)
     * @param mid   right boundary of the first subarray (inclusive)
     * @param right right boundary of the second subarray (inclusive)
     * @param <T>   the type of elements in the array
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right) {
        System.arraycopy(array, left, aux, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = mid + 1;

        for (int k = left; k <= right; k++) {
            if (leftIndex > mid) {
                array[k] = (T) aux[rightIndex++];
            } else if (rightIndex > right) {
                array[k] = (T) aux[leftIndex++];
            } else if (less(aux[rightIndex], aux[leftIndex])) {
                array[k] = (T) aux[rightIndex++];
            } else {
                array[k] = (T) aux[leftIndex++];
            }
        }
    }
}