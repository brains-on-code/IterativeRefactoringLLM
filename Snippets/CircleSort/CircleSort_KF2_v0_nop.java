package com.thealgorithms.sorts;

public class CircleSort implements SortAlgorithm {


    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (doSort(array, 0, array.length - 1)) {
        }
        return array;
    }


    private <T extends Comparable<T>> boolean doSort(final T[] array, final int left, final int right) {
        boolean swapped = false;

        if (left == right) {
            return false;
        }

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

        if (low == high && array[low].compareTo(array[high + 1]) > 0) {
            SortUtils.swap(array, low, high + 1);
            swapped = true;
        }

        final int mid = left + (right - left) / 2;
        final boolean leftHalfSwapped = doSort(array, left, mid);
        final boolean rightHalfSwapped = doSort(array, mid + 1, right);

        return swapped || leftHalfSwapped || rightHalfSwapped;
    }
}
