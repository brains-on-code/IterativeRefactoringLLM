package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        while (performCircleSort(array, 0, array.length - 1)) {
            // keep sorting until no swaps occur
        }

        return array;
    }

    private <T extends Comparable<T>> boolean performCircleSort(T[] array, int left, int right) {
        if (left >= right) {
            return false;
        }

        int low = left;
        int high = right;
        boolean swapped = false;

        while (low < high) {
            if (array[low].compareTo(array[high]) > 0) {
                SortUtils.swap(array, low, high);
                swapped = true;
            }
            low++;
            high--;
        }

        if (isMiddlePair(low, high, right)) {
            if (array[low].compareTo(array[high + 1]) > 0) {
                SortUtils.swap(array, low, high + 1);
                swapped = true;
            }
        }

        int mid = left + (right - left) / 2;
        boolean leftSwapped = performCircleSort(array, left, mid);
        boolean rightSwapped = performCircleSort(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }

    private boolean isMiddlePair(int low, int high, int right) {
        return low == high && high + 1 <= right;
    }
}