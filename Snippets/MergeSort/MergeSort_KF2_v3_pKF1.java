package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

class MergeSort implements SortAlgorithm {

    private Comparable[] auxiliaryArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        auxiliaryArray = new Comparable[array.length];
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

        System.arraycopy(array, left, auxiliaryArray, left, right + 1 - left);

        for (int current = left; current <= right; current++) {
            if (rightPointer > right) {
                array[current] = (T) auxiliaryArray[leftPointer++];
            } else if (leftPointer > middle) {
                array[current] = (T) auxiliaryArray[rightPointer++];
            } else if (less(auxiliaryArray[rightPointer], auxiliaryArray[leftPointer])) {
                array[current] = (T) auxiliaryArray[rightPointer++];
            } else {
                array[current] = (T) auxiliaryArray[leftPointer++];
            }
        }
    }
}