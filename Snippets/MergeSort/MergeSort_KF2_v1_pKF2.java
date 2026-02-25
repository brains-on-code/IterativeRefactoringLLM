package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 *
 * <p>Uses a single auxiliary array reused across recursive calls to minimize
 * allocations.</p>
 */
class MergeSort implements SortAlgorithm {

    /** Shared auxiliary array used during merge operations. */
    private Comparable<?>[] aux;

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        aux = new Comparable[unsorted.length];
        doSort(unsorted, 0, unsorted.length - 1);
        return unsorted;
    }

    /**
     * Recursively sorts the subarray {@code arr[left..right]} using merge sort.
     */
    private <T extends Comparable<T>> void doSort(T[] arr, int left, int right) {
        if (left >= right) {
            return; // Base case: single element (or empty) range is already sorted
        }

        int mid = (left + right) >>> 1; // Avoids potential overflow of (left + right) / 2
        doSort(arr, left, mid);         // Sort left half
        doSort(arr, mid + 1, right);    // Sort right half
        merge(arr, left, mid, right);   // Merge sorted halves
    }

    /**
     * Merges two consecutive sorted subarrays of {@code arr}:
     * <ul>
     *   <li>{@code arr[left..mid]}</li>
     *   <li>{@code arr[mid+1..right]}</li>
     * </ul>
     * into a single sorted segment {@code arr[left..right]}.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] arr, int left, int mid, int right) {
        // Copy the relevant slice of arr into the auxiliary array
        System.arraycopy(arr, left, aux, left, right - left + 1);

        int i = left;     // Pointer into left half in aux
        int j = mid + 1;  // Pointer into right half in aux

        // Merge back into arr[left..right]
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                // Left half exhausted; take from right half
                arr[k] = (T) aux[j++];
            } else if (j > right) {
                // Right half exhausted; take from left half
                arr[k] = (T) aux[i++];
            } else if (less(aux[j], aux[i])) {
                // Current element in right half is smaller
                arr[k] = (T) aux[j++];
            } else {
                // Current element in left half is smaller or equal
                arr[k] = (T) aux[i++];
            }
        }
    }
}