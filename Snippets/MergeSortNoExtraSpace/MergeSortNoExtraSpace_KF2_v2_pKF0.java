package com.thealgorithms.sorts;

import java.util.Arrays;

public final class MergeSortNoExtraSpace {

    private MergeSortNoExtraSpace() {
        // Utility class; prevent instantiation
    }

    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }

        validateNoNegativeNumbers(array);

        int maxElement = findMaxElement(array) + 1;
        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }

    private static void validateNoNegativeNumbers(int[] array) {
        boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }
    }

    private static int findMaxElement(int[] array) {
        return Arrays.stream(array).max().orElseThrow();
    }

    private static void mergeSort(int[] array, int start, int end, int maxElement) {
        if (start >= end) {
            return;
        }

        int middle = (start + end) >>> 1;
        mergeSort(array, start, middle, maxElement);
        mergeSort(array, middle + 1, end, maxElement);
        merge(array, start, middle, end, maxElement);
    }

    private static void merge(int[] array, int start, int middle, int end, int maxElement) {
        int leftIndex = start;
        int rightIndex = middle + 1;
        int mergeIndex = start;

        while (leftIndex <= middle && rightIndex <= end) {
            int leftValue = array[leftIndex] % maxElement;
            int rightValue = array[rightIndex] % maxElement;

            if (leftValue <= rightValue) {
                array[mergeIndex] += leftValue * maxElement;
                leftIndex++;
            } else {
                array[mergeIndex] += rightValue * maxElement;
                rightIndex++;
            }
            mergeIndex++;
        }

        while (leftIndex <= middle) {
            int leftValue = array[leftIndex] % maxElement;
            array[mergeIndex] += leftValue * maxElement;
            leftIndex++;
            mergeIndex++;
        }

        while (rightIndex <= end) {
            int rightValue = array[rightIndex] % maxElement;
            array[mergeIndex] += rightValue * maxElement;
            rightIndex++;
            mergeIndex++;
        }

        for (int i = start; i <= end; i++) {
            array[i] /= maxElement;
        }
    }
}