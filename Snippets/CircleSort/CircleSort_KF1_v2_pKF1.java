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

    private <T extends Comparable<T>> boolean partitionAndSwap(final T[] array, final int leftIndex, final int rightIndex) {
        boolean hasSwapped = false;

        if (leftIndex == rightIndex) {
            return false;
        }

        int leftPointer = leftIndex;
        int rightPointer = rightIndex;

        while (leftPointer < rightPointer) {
            if (array[leftPointer].compareTo(array[rightPointer]) > 0) {
                SortUtils.swap(array, leftPointer, rightPointer);
                hasSwapped = true;
            }
            leftPointer++;
            rightPointer--;
        }

        if (leftPointer == rightPointer && array[leftPointer].compareTo(array[rightPointer + 1]) > 0) {
            SortUtils.swap(array, leftPointer, rightPointer + 1);
            hasSwapped = true;
        }

        final int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
        final boolean leftPartitionSwapped = partitionAndSwap(array, leftIndex, middleIndex);
        final boolean rightPartitionSwapped = partitionAndSwap(array, middleIndex + 1, rightIndex);

        return hasSwapped || leftPartitionSwapped || rightPartitionSwapped;
    }
}