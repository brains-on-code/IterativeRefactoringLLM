package com.thealgorithms.sorts;

public class SymmetryPartitionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (partitionAndSwap(array, 0, array.length - 1)) {
            // keep iterating until no swaps occur
        }
        return array;
    }

    private <T extends Comparable<T>> boolean partitionAndSwap(final T[] array, final int start, final int end) {
        if (start == end) {
            return false;
        }

        boolean hasSwapped = false;
        int left = start;
        int right = end;

        while (left < right) {
            if (array[left].compareTo(array[right]) > 0) {
                SortUtils.swap(array, left, right);
                hasSwapped = true;
            }
            left++;
            right--;
        }

        if (left == right && array[left].compareTo(array[right + 1]) > 0) {
            SortUtils.swap(array, left, right + 1);
            hasSwapped = true;
        }

        final int middle = start + (end - start) / 2;
        final boolean leftPartitionSwapped = partitionAndSwap(array, start, middle);
        final boolean rightPartitionSwapped = partitionAndSwap(array, middle + 1, end);

        return hasSwapped || leftPartitionSwapped || rightPartitionSwapped;
    }
}