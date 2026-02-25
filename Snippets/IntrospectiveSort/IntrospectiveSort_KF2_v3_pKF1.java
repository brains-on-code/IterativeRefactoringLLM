package com.thealgorithms.sorts;

public class IntrospectiveSort implements SortAlgorithm {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        final int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introspectiveSort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    private static <T extends Comparable<T>> void introspectiveSort(
            T[] array, final int leftIndex, int rightIndex, final int maxDepth) {

        int currentRightIndex = rightIndex;
        int currentDepth = maxDepth;

        while (currentRightIndex - leftIndex > INSERTION_SORT_THRESHOLD) {
            if (currentDepth == 0) {
                heapSort(array, leftIndex, currentRightIndex);
                return;
            }
            final int pivotIndex = partition(array, leftIndex, currentRightIndex);
            introspectiveSort(array, pivotIndex + 1, currentRightIndex, currentDepth - 1);
            currentRightIndex = pivotIndex - 1;
            currentDepth--;
        }
        insertionSort(array, leftIndex, currentRightIndex);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array, final int leftIndex, final int rightIndex) {

        final int randomPivotIndex =
                leftIndex + (int) (Math.random() * (rightIndex - leftIndex + 1));
        SortUtils.swap(array, randomPivotIndex, rightIndex);

        final T pivotValue = array[rightIndex];
        int smallerElementBoundary = leftIndex - 1;

        for (int currentIndex = leftIndex; currentIndex < rightIndex; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, currentIndex);
            }
        }
        SortUtils.swap(array, smallerElementBoundary + 1, rightIndex);
        return smallerElementBoundary + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array, final int leftIndex, final int rightIndex) {

        for (int currentIndex = leftIndex + 1; currentIndex <= rightIndex; currentIndex++) {
            final T currentValue = array[currentIndex];
            int previousIndex = currentIndex - 1;

            while (previousIndex >= leftIndex && array[previousIndex].compareTo(currentValue) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }
            array[previousIndex + 1] = currentValue;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array, final int leftIndex, final int rightIndex) {

        final int heapSize = rightIndex - leftIndex + 1;

        for (int rootIndex = (heapSize / 2) - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, rootIndex, heapSize, leftIndex);
        }

        for (int currentEndIndex = rightIndex; currentEndIndex > leftIndex; currentEndIndex--) {
            SortUtils.swap(array, leftIndex, currentEndIndex);
            heapify(array, 0, currentEndIndex - leftIndex, leftIndex);
        }
    }

    private static <T extends Comparable<T>> void heapify(
            T[] array, final int rootIndex, final int heapSize, final int offset) {

        final int leftChildIndex = 2 * rootIndex + 1;
        final int rightChildIndex = 2 * rootIndex + 2;
        int largestIndex = rootIndex;

        if (leftChildIndex < heapSize
                && array[offset + leftChildIndex].compareTo(array[offset + largestIndex]) > 0) {
            largestIndex = leftChildIndex;
        }

        if (rightChildIndex < heapSize
                && array[offset + rightChildIndex].compareTo(array[offset + largestIndex]) > 0) {
            largestIndex = rightChildIndex;
        }

        if (largestIndex != rootIndex) {
            SortUtils.swap(array, offset + rootIndex, offset + largestIndex);
            heapify(array, largestIndex, heapSize, offset);
        }
    }
}