package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }

        // Keep applying circle sort passes until a full pass occurs with no swaps
        while (circleSort(array, 0, array.length - 1)) {
            // Intentionally empty: work is done in circleSort
        }

        return array;
    }

    private <T extends Comparable<T>> boolean circleSort(final T[] array, final int left, final int right) {
        if (left >= right) {
            return false;
        }

        boolean swapped = false;
        int low = left;
        int high = right;

        // Compare and swap symmetric elements from the ends towards the center
        while (low < high) {
            if (array[low].compareTo(array[high]) > 0) {
                SortUtils.swap(array, low, high);
                swapped = true;
            }
            low++;
            high--;
        }

        // If there is a middle element (odd-length segment), compare it with its right neighbor
        if (low == high && high + 1 <= right && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        int mid = left + (right - left) / 2;

        // Recursively apply circle sort to left and right halves
        boolean leftHalfSwapped = circleSort(array, left, mid);
        boolean rightHalfSwapped = circleSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }
}