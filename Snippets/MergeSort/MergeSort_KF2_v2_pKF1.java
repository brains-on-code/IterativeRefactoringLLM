package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

class MergeSort implements SortAlgorithm {

    private Comparable[] tempArray;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        tempArray = new Comparable[array.length];
        mergeSort(array, 0, array.length - 1);
        return array;
    }

    private <T extends Comparable<T>> void mergeSort(T[] array, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int middleIndex = (startIndex + endIndex) >>> 1;
            mergeSort(array, startIndex, middleIndex);
            mergeSort(array, middleIndex + 1, endIndex);
            merge(array, startIndex, middleIndex, endIndex);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void merge(T[] array, int startIndex, int middleIndex, int endIndex) {
        int leftIndex = startIndex;
        int rightIndex = middleIndex + 1;

        System.arraycopy(array, startIndex, tempArray, startIndex, endIndex + 1 - startIndex);

        for (int currentIndex = startIndex; currentIndex <= endIndex; currentIndex++) {
            if (rightIndex > endIndex) {
                array[currentIndex] = (T) tempArray[leftIndex++];
            } else if (leftIndex > middleIndex) {
                array[currentIndex] = (T) tempArray[rightIndex++];
            } else if (less(tempArray[rightIndex], tempArray[leftIndex])) {
                array[currentIndex] = (T) tempArray[rightIndex++];
            } else {
                array[currentIndex] = (T) tempArray[leftIndex++];
            }
        }
    }
}