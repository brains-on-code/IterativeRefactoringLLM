package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        while (circleSort(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
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

        // Compare and swap symmetric elements
        while (low < high) {
            if (array[low].compareTo(array[high]) > 0) {
                SortUtils.swap(array, low, high);
                swapped = true;
            }
            low++;
            high--;
        }

        // Handle the middle element in odd-length segments
        if (low == high && high + 1 <= right && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        int mid = left + (right - left) / 2;

        boolean leftHalfSwapped = circleSort(array, left, mid);
        boolean rightHalfSwapped = circleSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }
}