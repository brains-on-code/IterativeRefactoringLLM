package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Generic merge sort algorithm.
 *
 * @see SortAlgorithm
 */
class MergeSort implements SortAlgorithm {

    private Comparable[] auxiliaryArray;

    /**
     * Generic merge sort algorithm implementation.
     *
     * @param unsortedArray the array which should be sorted.
     * @param <T> Comparable class.
     * @return sorted array.
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsortedArray) {
        auxiliaryArray = new Comparable[unsortedArray.length];
        sortRange(unsortedArray, 0, unsortedArray.length - 1);
        return unsortedArray;
    }

    /**
     * Recursively sorts the specified range of the array.
     *
     * @param array the array to be sorted.
     * @param leftIndex the first index of the range.
     * @param rightIndex the last index of the range.
     */
    private <T extends Comparable<T>> void sortRange(T[] array, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            sortRange(array, leftIndex, middleIndex);
            sortRange(array, middleIndex + 1, rightIndex);
            merge(array, leftIndex, middleIndex, rightIndex);
        }
    }

    /**
     * Merges two sorted subranges of the array.
     *
     * @param array the array containing the subranges to be merged.
     * @param leftIndex the first index of the left subrange.
     * @param middleIndex the last index of the left subrange (middle point).
     * @param rightIndex the last index of the right subrange.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int leftIndex, int middleIndex, int rightIndex) {
        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;

        System.arraycopy(array, leftIndex, auxiliaryArray, leftIndex, rightIndex + 1 - leftIndex);

        for (int currentIndex = leftIndex; currentIndex <= rightIndex; currentIndex++) {
            if (rightPointer > rightIndex) {
                array[currentIndex] = (T) auxiliaryArray[leftPointer++];
            } else if (leftPointer > middleIndex) {
                array[currentIndex] = (T) auxiliaryArray[rightPointer++];
            } else if (less(auxiliaryArray[rightPointer], auxiliaryArray[leftPointer])) {
                array[currentIndex] = (T) auxiliaryArray[rightPointer++];
            } else {
                array[currentIndex] = (T) auxiliaryArray[leftPointer++];
            }
        }
    }
}