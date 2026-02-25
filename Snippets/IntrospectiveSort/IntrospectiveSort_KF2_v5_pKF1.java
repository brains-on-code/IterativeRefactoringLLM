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
        int remainingDepth = maxDepth;

        while (currentRight - left > INSERTION_SORT_THRESHOLD) {
            if (remainingDepth == 0) {
                heapSort(array, left, currentRight);
                return;
            }
            final int pivotIndex = partition(array, left, currentRight);
            introspectiveSort(array, pivotIndex + 1, currentRight, remainingDepth - 1);
            currentRight = pivotIndex - 1;
            remainingDepth--;
        }
        insertionSort(array, left, currentRight);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array, final int left, final int right) {

        final int randomPivotIndex =
                left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, randomPivotIndex, right);

        final T pivot = array[right];
        int lastSmallerOrEqualIndex = left - 1;

        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (array[currentIndex].compareTo(pivot) <= 0) {
                lastSmallerOrEqualIndex++;
                SortUtils.swap(array, lastSmallerOrEqualIndex, currentIndex);
            }
        }
        SortUtils.swap(array, lastSmallerOrEqualIndex + 1, right);
        return lastSmallerOrEqualIndex + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array, final int left, final int right) {

        for (int currentIndex = left + 1; currentIndex <= right; currentIndex++) {
            final T key = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= left && array[sortedIndex].compareTo(key) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = key;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array, final int left, final int right) {

        final int heapSize = right - left + 1;

        for (int rootIndex = (heapSize / 2) - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, rootIndex, heapSize, left);
        }

        for (int currentRight = right; currentRight > left; currentRight--) {
            SortUtils.swap(array, left, currentRight);
            heapify(array, 0, currentRight - left, left);
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