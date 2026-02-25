package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    private Comparable[] tempArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        tempArray = new Comparable[array.length];
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
        int leftPointer = left;
        int rightPointer = middle + 1;

        System.arraycopy(array, left, tempArray, left, right + 1 - left);

        for (int current = left; current <= right; current++) {
            if (rightPointer > right) {
                array[current] = (T) tempArray[leftPointer++];
            } else if (leftPointer > middle) {
                array[current] = (T) tempArray[rightPointer++];
            } else if (less(tempArray[rightPointer], tempArray[leftPointer])) {
                array[current] = (T) tempArray[rightPointer++];
            } else {
                array[current] = (T) tempArray[leftPointer++];
            }
        }
    }
}