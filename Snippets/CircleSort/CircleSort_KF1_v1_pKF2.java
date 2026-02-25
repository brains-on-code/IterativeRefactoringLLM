package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    /**
     * Sorts the given array using a recursive pairwise comparison algorithm.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (pairwiseSort(array, 0, array.length - 1)) {
            // keep iterating while swaps are still being made
        }
        return array;
    }

    /**
     * Performs a single pass of pairwise comparisons and swaps on the given range
     * of the array, then recursively processes the left and right halves.
     *
     * The method:
     * 1. Compares elements symmetrically from the ends of the range toward the center.
     * 2. Swaps any out-of-order pairs.
     * 3. Optionally fixes the middle element if the range length is odd.
     * 4. Recursively processes the left and right subranges.
     *
     * @param array the array to be processed
     * @param left  the starting index of the range (inclusive)
     * @param right the ending index of the range (inclusive)
     * @param <T>   the type of elements, must be comparable
     * @return true if any swap was performed in this call or its recursive calls,
     *         false otherwise
     */
    private <T extends Comparable<T>> boolean pairwiseSort(final T[] array, final int left, final int right) {
        boolean swapped = false;

        if (left == right) {
            return false;
        }

        int i = left;
        int j = right;

        // Compare and swap symmetric elements from both ends toward the center
        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                swapped = true;
            }
            i++;
            j--;
        }

        // If there is a middle element (odd length), ensure it is not greater than the next element
        if (i == j && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            swapped = true;
        }

        final int mid = left + (right - left) / 2;
        final boolean leftSwapped = pairwiseSort(array, left, mid);
        final boolean rightSwapped = pairwiseSort(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }
}