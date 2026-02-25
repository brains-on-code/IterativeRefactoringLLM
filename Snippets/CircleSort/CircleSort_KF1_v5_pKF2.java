package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array.length == 0) {
            return array;
        }

        // Continue recursive pairwise sorting while any swaps occur
        while (pairwiseSort(array, 0, array.length - 1)) {
            // Intentionally empty: loop condition drives the process
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

        // Compare and swap elements from both ends, moving toward the center
        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                swapped = true;
            }
            i++;
            j--;
        }

        // If the subarray length is odd, adjust the middle element if needed
        if (i == j && j + 1 <= right && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            swapped = true;
        }

        final int mid = left + (right - left) / 2;

        boolean leftSwapped = pairwiseSort(array, left, mid);
        boolean rightSwapped = pairwiseSort(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }
}