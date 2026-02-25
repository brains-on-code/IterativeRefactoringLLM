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

    public static void mergeSort(int[] array, int left, int right, int encodingBase) {
        if (left < right) {
            final int mid = (left + right) >>> 1;
            mergeSort(array, left, mid, encodingBase);
            mergeSort(array, mid + 1, right, encodingBase);
            merge(array, left, mid, right, encodingBase);
        }
    }

    private static void merge(int[] array, int left, int mid, int right, int encodingBase) {
        int leftIndex = left;
        int rightIndex = mid + 1;
        int mergedIndex = left;

        while (leftIndex <= mid && rightIndex <= right) {
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

        while (leftIndex <= mid) {
            int leftValue = array[leftIndex] % encodingBase;
            array[mergedIndex] = array[mergedIndex] + leftValue * encodingBase;
            leftIndex++;
            mergedIndex++;
        }

        while (rightIndex <= right) {
            int rightValue = array[rightIndex] % encodingBase;
            array[mergedIndex] = array[mergedIndex] + rightValue * encodingBase;
            rightIndex++;
            mergedIndex++;
        }

        for (int index = left; index <= right; index++) {
            array[index] = array[index] / encodingBase;
        }
    }
}