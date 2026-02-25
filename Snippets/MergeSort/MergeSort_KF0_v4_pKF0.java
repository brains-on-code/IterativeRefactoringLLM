package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Generic merge sort algorithm.
 *
 * @see SortAlgorithm
 */
class MergeSort implements SortAlgorithm {

    private Comparable<?>[] auxiliaryArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        auxiliaryArray = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        mergeSort(array, left, middle);
        mergeSort(array, middle + 1, right);
        merge(array, left, middle, right);
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int left, int middle, int right) {
        System.arraycopy(array, left, auxiliaryArray, left, right - left + 1);

        int leftIndex = left;
        int rightIndex = middle + 1;

        for (int currentIndex = left; currentIndex <= right; currentIndex++) {
            boolean leftExhausted = leftIndex > middle;
            boolean rightExhausted = rightIndex > right;

            if (leftExhausted) {
                array[currentIndex] = (T) auxiliaryArray[rightIndex++];
            } else if (rightExhausted) {
                array[currentIndex] = (T) auxiliaryArray[leftIndex++];
            } else if (less(auxiliaryArray[rightIndex], auxiliaryArray[leftIndex])) {
                array[currentIndex] = (T) auxiliaryArray[rightIndex++];
            } else {
                array[currentIndex] = (T) auxiliaryArray[leftIndex++];
            }
        }
    }
}