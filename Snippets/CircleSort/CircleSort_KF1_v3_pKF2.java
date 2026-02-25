package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array.length == 0) {
            return array;
        }

        // Continue sorting while any swaps are being made in a pass
        while (pairwiseSort(array, 0, array.length - 1)) {
        }

        return array;
    }

    private <T extends Comparable<T>> boolean pairwiseSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return false;
        }

        boolean swapped = false;
        int i = left;
        int j = right;

        // Compare and swap elements in pairs from the ends towards the center
        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                swapped = true;
            }
            i++;
            j--;
        }

        // Handle the middle element when the subarray has odd length
        if (i == j && j + 1 <= right && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            swapped = true;
        }

        final int mid = left + (right - left) / 2;

        // Recursively sort left and right halves
        final boolean leftSwapped = pairwiseSort(array, left, mid);
        final boolean rightSwapped = pairwiseSort(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }
}