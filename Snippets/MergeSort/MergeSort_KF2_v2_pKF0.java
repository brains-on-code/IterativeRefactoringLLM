package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

class MergeSort implements SortAlgorithm {

    private Comparable<?>[] auxiliaryArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        auxiliaryArray = new Comparable<?>[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int leftIndex, int rightIndex) {
        if (leftIndex >= rightIndex) {
            return;
        }

        int middleIndex = leftIndex + ((rightIndex - leftIndex) >>> 1);
        mergeSort(array, leftIndex, middleIndex);
        mergeSort(array, middleIndex + 1, rightIndex);
        merge(array, leftIndex, middleIndex, rightIndex);
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int leftIndex, int middleIndex, int rightIndex) {
        System.arraycopy(array, leftIndex, auxiliaryArray, leftIndex, rightIndex - leftIndex + 1);

        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;

        for (int currentIndex = leftIndex; currentIndex <= rightIndex; currentIndex++) {
            if (leftPointer > middleIndex) {
                array[currentIndex] = (T) auxiliaryArray[rightPointer++];
            } else if (rightPointer > rightIndex) {
                array[currentIndex] = (T) auxiliaryArray[leftPointer++];
            } else if (less(auxiliaryArray[rightPointer], auxiliaryArray[leftPointer])) {
                array[currentIndex] = (T) auxiliaryArray[rightPointer++];
            } else {
                array[currentIndex] = (T) auxiliaryArray[leftPointer++];
            }
        }
    }
}