package com.thealgorithms.sorts;

import java.util.Arrays;

public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
    }

    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        if (Arrays.stream(array).anyMatch(value -> value < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        final int maxElementPlusOne = Arrays.stream(array).max().getAsInt() + 1;
        mergeSort(array, 0, array.length - 1, maxElementPlusOne);
        return array;
    }

    public static void mergeSort(int[] array, int leftIndex, int rightIndex, int maxElementPlusOne) {
        if (leftIndex < rightIndex) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            mergeSort(array, leftIndex, middleIndex, maxElementPlusOne);
            mergeSort(array, middleIndex + 1, rightIndex, maxElementPlusOne);
            merge(array, leftIndex, middleIndex, rightIndex, maxElementPlusOne);
        }
    }

    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex, int maxElementPlusOne) {
        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;
        int mergePointer = leftIndex;

        while (leftPointer <= middleIndex && rightPointer <= rightIndex) {
            int leftValue = array[leftPointer] % maxElementPlusOne;
            int rightValue = array[rightPointer] % maxElementPlusOne;

            if (leftValue <= rightValue) {
                array[mergePointer] = array[mergePointer] + leftValue * maxElementPlusOne;
                leftPointer++;
            } else {
                array[mergePointer] = array[mergePointer] + rightValue * maxElementPlusOne;
                rightPointer++;
            }
            mergePointer++;
        }

        while (leftPointer <= middleIndex) {
            int leftValue = array[leftPointer] % maxElementPlusOne;
            array[mergePointer] = array[mergePointer] + leftValue * maxElementPlusOne;
            leftPointer++;
            mergePointer++;
        }

        while (rightPointer <= rightIndex) {
            int rightValue = array[rightPointer] % maxElementPlusOne;
            array[mergePointer] = array[mergePointer] + rightValue * maxElementPlusOne;
            rightPointer++;
            mergePointer++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] = array[index] / maxElementPlusOne;
        }
    }
}