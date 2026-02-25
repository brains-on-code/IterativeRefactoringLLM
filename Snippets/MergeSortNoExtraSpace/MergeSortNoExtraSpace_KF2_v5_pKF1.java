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

    private static void merge(int[] array, int leftStart, int middleIndex, int rightEnd, int encodingBase) {
        int leftIndex = leftStart;
        int rightIndex = middleIndex + 1;
        int mergedIndex = leftStart;

        while (leftIndex <= middleIndex && rightIndex <= rightEnd) {
            int leftValue = array[leftIndex] % encodingBase;
            int rightValue = array[rightIndex] % encodingBase;

            if (leftValue <= rightValue) {
                array[mergedIndex] = array[mergedIndex] + leftValue * encodingBase;
                leftIndex++;
            } else {
                array[mergedIndex] = array[mergedIndex] + rightValue * encodingBase;
                rightIndex++;
            }
            mergedIndex++;
        }

        while (leftIndex <= middleIndex) {
            int leftValue = array[leftIndex] % encodingBase;
            array[mergedIndex] = array[mergedIndex] + leftValue * encodingBase;
            leftIndex++;
            mergedIndex++;
        }

        while (rightIndex <= rightEnd) {
            int rightValue = array[rightIndex] % encodingBase;
            array[mergedIndex] = array[mergedIndex] + rightValue * encodingBase;
            rightIndex++;
            mergedIndex++;
        }

        for (int index = leftStart; index <= rightEnd; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}