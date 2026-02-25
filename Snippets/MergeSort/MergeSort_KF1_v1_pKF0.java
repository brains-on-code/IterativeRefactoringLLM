package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    private Comparable[] auxiliary;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        auxiliary = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) >>> 1;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int middle, int right) {
        int leftIndex = left;
        int rightIndex = middle + 1;

        System.arraycopy(array, left, auxiliary, left, right + 1 - left);

        for (int current = left; current <= right; current++) {
            if (rightIndex > right) {
                array[current] = (T) auxiliary[leftIndex++];
            } else if (leftIndex > middle) {
                array[current] = (T) auxiliary[rightIndex++];
            } else if (less(auxiliary[rightIndex], auxiliary[leftIndex])) {
                array[current] = (T) auxiliary[rightIndex++];
            } else {
                array[current] = (T) auxiliary[leftIndex++];
            }
        }
    }
}