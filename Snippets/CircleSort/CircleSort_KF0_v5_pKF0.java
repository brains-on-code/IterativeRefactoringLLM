package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        while (performCircleSort(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
        }

        return array;
    }

    private <T extends Comparable<T>> boolean performCircleSort(final T[] array, final int left, final int right) {
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

        if (isOddSegmentMiddleElement(low, high, right) && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        int mid = left + (right - left) / 2;

        boolean leftHalfSwapped = performCircleSort(array, left, mid);
        boolean rightHalfSwapped = performCircleSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }

    private boolean isOddSegmentMiddleElement(int low, int high, int right) {
        return low == high && high + 1 <= right;
    }
}