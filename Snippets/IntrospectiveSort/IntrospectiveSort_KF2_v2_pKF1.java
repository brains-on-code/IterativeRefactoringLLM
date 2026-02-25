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
            T[] array, final int left, int right, final int maxDepth) {

        int currentRight = right;
        int currentDepth = maxDepth;

        while (currentRight - left > INSERTION_SORT_THRESHOLD) {
            if (currentDepth == 0) {
                heapSort(array, left, currentRight);
                return;
            }
            final int pivotIndex = partition(array, left, currentRight);
            introspectiveSort(array, pivotIndex + 1, currentRight, currentDepth - 1);
            currentRight = pivotIndex - 1;
            currentDepth--;
        }
        insertionSort(array, left, currentRight);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array, final int left, final int right) {

        final int randomPivotIndex =
                left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, randomPivotIndex, right);

        final T pivot = array[right];
        int smallerElementBoundary = left - 1;

        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (array[currentIndex].compareTo(pivot) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, currentIndex);
            }
        }
        SortUtils.swap(array, smallerElementBoundary + 1, right);
        return smallerElementBoundary + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array, final int left, final int right) {

        for (int currentIndex = left + 1; currentIndex <= right; currentIndex++) {
            final T currentValue = array[currentIndex];
            int previousIndex = currentIndex - 1;

            while (previousIndex >= left && array[previousIndex].compareTo(currentValue) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }
            array[previousIndex + 1] = currentValue;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array, final int left, final int right) {

        final int heapSize = right - left + 1;

        for (int rootIndex = (heapSize / 2) - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, rootIndex, heapSize, left);
        }

        for (int currentEnd = right; currentEnd > left; currentEnd--) {
            SortUtils.swap(array, left, currentEnd);
            heapify(array, 0, currentEnd - left, left);
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