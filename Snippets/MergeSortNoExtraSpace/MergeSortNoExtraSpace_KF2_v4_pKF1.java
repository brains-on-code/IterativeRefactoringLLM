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

    public static void mergeSort(int[] array, int leftIndex, int rightIndex, int encodingBase) {
        if (leftIndex < rightIndex) {
            final int middleIndex = (leftIndex + rightIndex) >>> 1;
            mergeSort(array, leftIndex, middleIndex, encodingBase);
            mergeSort(array, middleIndex + 1, rightIndex, encodingBase);
            merge(array, leftIndex, middleIndex, rightIndex, encodingBase);
        }
    }

    private static void merge(int[] array, int leftIndex, int middleIndex, int rightIndex, int encodingBase) {
        int leftPointer = leftIndex;
        int rightPointer = middleIndex + 1;
        int mergeIndex = leftIndex;

        while (leftPointer <= middleIndex && rightPointer <= rightIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            int rightValue = array[rightPointer] % encodingBase;

            if (leftValue <= rightValue) {
                array[mergeIndex] = array[mergeIndex] + leftValue * encodingBase;
                leftPointer++;
            } else {
                array[mergeIndex] = array[mergeIndex] + rightValue * encodingBase;
                rightPointer++;
            }
            mergeIndex++;
        }

        while (leftPointer <= middleIndex) {
            int leftValue = array[leftPointer] % encodingBase;
            array[mergeIndex] = array[mergeIndex] + leftValue * encodingBase;
            leftPointer++;
            mergeIndex++;
        }

        while (rightPointer <= rightIndex) {
            int rightValue = array[rightPointer] % encodingBase;
            array[mergeIndex] = array[mergeIndex] + rightValue * encodingBase;
            rightPointer++;
            mergeIndex++;
        }

        for (int index = leftIndex; index <= rightIndex; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}