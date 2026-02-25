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

        ensureAllNonNegative(array);

        int maxElement = findMaxElement(array) + 1;
        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }

    private static void ensureAllNonNegative(int[] array) {
        boolean hasNegative = Arrays.stream(array).anyMatch(value -> value < 0);
        if (hasNegative) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }
    }

    private static int findMaxElement(int[] array) {
        return Arrays.stream(array).max().orElseThrow();
    }

    private static void mergeSort(int[] array, int left, int right, int maxElement) {
        if (left >= right) {
            return;
        }

        int middle = (left + right) >>> 1;
        mergeSort(array, left, middle, maxElement);
        mergeSort(array, middle + 1, right, maxElement);
        merge(array, left, middle, right, maxElement);
    }

    private static void merge(int[] array, int left, int middle, int right, int maxElement) {
        int leftIndex = left;
        int rightIndex = middle + 1;
        int writeIndex = left;

        while (leftIndex <= middle && rightIndex <= right) {
            int leftValue = decodeValue(array[leftIndex], maxElement);
            int rightValue = decodeValue(array[rightIndex], maxElement);

            if (leftValue <= rightValue) {
                encodeValue(array, writeIndex++, leftValue, maxElement);
                leftIndex++;
            } else {
                encodeValue(array, writeIndex++, rightValue, maxElement);
                rightIndex++;
            }
        }

        while (leftIndex <= middle) {
            int leftValue = decodeValue(array[leftIndex++], maxElement);
            encodeValue(array, writeIndex++, leftValue, maxElement);
        }

        while (rightIndex <= right) {
            int rightValue = decodeValue(array[rightIndex++], maxElement);
            encodeValue(array, writeIndex++, rightValue, maxElement);
        }

        decodeRange(array, left, right, maxElement);
    }

    private static int decodeValue(int encodedValue, int maxElement) {
        return encodedValue % maxElement;
    }

    private static void encodeValue(int[] array, int index, int value, int maxElement) {
        array[index] += value * maxElement;
    }

    private static void decodeRange(int[] array, int start, int end, int maxElement) {
        for (int i = start; i <= end; i++) {
            array[i] /= maxElement;
        }
    }
}