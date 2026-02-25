package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * Merge sort implementation.
 */
class MergeSort implements SortAlgorithm {

    private Comparable[] auxiliaryArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        auxiliaryArray = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) >>> 1;
            mergeSort(array, leftIndex, middleIndex);
            mergeSort(array, middleIndex + 1, rightIndex);
            merge(array, leftIndex, middleIndex, rightIndex);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int leftIndex, int middleIndex, int rightIndex) {
        int leftSubarrayIndex = leftIndex;
        int rightSubarrayIndex = middleIndex + 1;

        System.arraycopy(array, leftIndex, auxiliaryArray, leftIndex, rightIndex + 1 - leftIndex);

        for (int mergedIndex = leftIndex; mergedIndex <= rightIndex; mergedIndex++) {
            if (rightSubarrayIndex > rightIndex) {
                array[mergedIndex] = (T) auxiliaryArray[leftSubarrayIndex++];
            } else if (leftSubarrayIndex > middleIndex) {
                array[mergedIndex] = (T) auxiliaryArray[rightSubarrayIndex++];
            } else if (less(auxiliaryArray[rightSubarrayIndex], auxiliaryArray[leftSubarrayIndex])) {
                array[mergedIndex] = (T) auxiliaryArray[rightSubarrayIndex++];
            } else {
                array[mergedIndex] = (T) auxiliaryArray[leftSubarrayIndex++];
            }
        }
    }
}