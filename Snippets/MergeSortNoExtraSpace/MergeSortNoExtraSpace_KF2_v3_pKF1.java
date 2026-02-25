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

        final int encodingBase = Arrays.stream(array).max().getAsInt() + 1;
        mergeSort(array, 0, array.length - 1, encodingBase);
        return array;
    }

    public static void mergeSort(int[] array, int startIndex, int endIndex, int encodingBase) {
        if (startIndex < endIndex) {
            final int middleIndex = (startIndex + endIndex) >>> 1;
            mergeSort(array, startIndex, middleIndex, encodingBase);
            mergeSort(array, middleIndex + 1, endIndex, encodingBase);
            merge(array, startIndex, middleIndex, endIndex, encodingBase);
        }
    }

    private static void merge(int[] array, int startIndex, int middleIndex, int endIndex, int encodingBase) {
        int leftPointer = startIndex;
        int rightPointer = middleIndex + 1;
        int mergePointer = startIndex;

        while (leftPointer <= middleIndex && rightPointer <= endIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            int rightValue = array[rightPointer] % encodingBase;

            if (leftValue <= rightValue) {
                array[mergePointer] = array[mergePointer] + leftValue * encodingBase;
                leftPointer++;
            } else {
                array[mergePointer] = array[mergePointer] + rightValue * encodingBase;
                rightPointer++;
            }
            mergePointer++;
        }

        while (leftPointer <= middleIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            array[mergePointer] = array[mergePointer] + leftValue * encodingBase;
            leftPointer++;
            mergePointer++;
        }

        while (rightPointer <= endIndex) {
            int rightValue = array[rightPointer] % encodingBase;
            array[mergePointer] = array[mergePointer] + rightValue * encodingBase;
            rightPointer++;
            mergePointer++;
        }

        for (int index = startIndex; index <= endIndex; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}