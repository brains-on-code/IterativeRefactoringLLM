package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        // Repeat passes until no swaps occur
        while (doSort(array, 0, array.length - 1)) {
            // Intentionally empty: loop continues while array is being changed
        }

        return array;
    }

    /**
     * Recursively performs a "circle sort" on the subarray [left, right].
     *
     * @param array the array to sort
     * @param left  left index of the current segment
     * @param right right index of the current segment
     * @return true if any swap occurred in this call or its recursive calls
     */
    private <T extends Comparable<T>> boolean doSort(final T[] array, final int left, final int right) {
        if (left == right) {
            return false; // Single element is already sorted
        }

        boolean swapped = false;
        int low = left;
        int high = right;

        // Compare and swap elements in "circle" pairs: (left, right), (left+1, right-1), ...
        while (low < high) {
            if (array[low].compareTo(array[high]) > 0) {
                SortUtils.swap(array, low, high);
                swapped = true;
            }
            low++;
            high--;
        }

        // If there is a middle element (odd-length segment), compare it with its next element
        if (low == high && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        // Recursively sort left and right halves
        final int mid = left + (right - left) / 2;
        final boolean leftHalfSwapped = doSort(array, left, mid);
        final boolean rightHalfSwapped = doSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }
}