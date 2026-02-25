package com.thealgorithms.sorts;

import java.util.Arrays;


public final class MergeSortNoExtraSpace {
    private MergeSortNoExtraSpace() {
    }


    public static int[] sort(int[] array) {
        if (array.length == 0) {
            return array;
        }
        if (Arrays.stream(array).anyMatch(s -> s < 0)) {
            throw new IllegalArgumentException("Implementation cannot sort negative numbers.");
        }

        final int maxElement = Arrays.stream(array).max().getAsInt() + 1;
        mergeSort(array, 0, array.length - 1, maxElement);
        return array;
    }


    public static void mergeSort(int[] array, int start, int end, int maxElement) {
        if (start < end) {
            final int middle = (start + end) >>> 1;
            mergeSort(array, start, middle, maxElement);
            mergeSort(array, middle + 1, end, maxElement);
            merge(array, start, middle, end, maxElement);
        }
    }


    private static void merge(int[] array, int start, int middle, int end, int maxElement) {
        int i = start;
        int j = middle + 1;
        int k = start;
        while (i <= middle && j <= end) {
            if (array[i] % maxElement <= array[j] % maxElement) {
                array[k] = array[k] + (array[i] % maxElement) * maxElement;
                k++;
                i++;
            } else {
                array[k] = array[k] + (array[j] % maxElement) * maxElement;
                k++;
                j++;
            }
        }
        while (i <= middle) {
            array[k] = array[k] + (array[i] % maxElement) * maxElement;
            k++;
            i++;
        }
        while (j <= end) {
            array[k] = array[k] + (array[j] % maxElement) * maxElement;
            k++;
            j++;
        }
        for (i = start; i <= end; i++) {
            array[i] = array[i] / maxElement;
        }
    }
}
