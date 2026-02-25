package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        while (performRecursivePairwiseSwap(array, 0, array.length - 1)) {
            // keep iterating until no swaps occur
        }

        return array;
    }

    private <T extends Comparable<T>> boolean performRecursivePairwiseSwap(final T[] array, final int left, final int right) {
        if (left >= right) {
            return false;
        }

        boolean swapped = false;
        int i = left;
        int j = right;

        // Pairwise swap from both ends towards the center
        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                swapped = true;
            }
            i++;
            j--;
        }

        // Handle middle element when the segment length is odd
        if (i == j && j + 1 <= right && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            swapped = true;
        }

        int mid = left + (right - left) / 2;
        boolean leftSwapped = performRecursivePairwiseSwap(array, left, mid);
        boolean rightSwapped = performRecursivePairwiseSwap(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }
}