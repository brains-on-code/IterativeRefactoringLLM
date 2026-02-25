package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    /**
     * Sorts the given array using the Circle Sort algorithm.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements in the array, which must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        while (circleSort(array, 0, array.length - 1)) {
            // keep sorting while swaps are being made
        }

        return array;
    }

    /**
     * Recursively sorts the array by comparing elements in pairs from the
     * outer ends of the current segment moving inward, then recursing on
     * the left and right halves.
     *
     * @param array the array to be sorted
     * @param left  the left index of the current segment
     * @param right the right index of the current segment
     * @param <T>   the type of elements in the array, which must be comparable
     * @return true if any elements were swapped; false otherwise
     */
    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return false;
        }

        boolean swapped = false;
        int low = left;
        int high = right;

        while (low < high) {
            if (array[low].compareTo(array[high]) > 0) {
                SortUtils.swap(array, low, high);
                swapped = true;
            }
            low++;
            high--;
        }

        // Handle the middle element in an odd-length segment
        if (low == high && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        int mid = left + (right - left) / 2;
        boolean leftHalfSwapped = circleSort(array, left, mid);
        boolean rightHalfSwapped = circleSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }
}