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
            T[] array, final int startIndex, int endIndex, final int maxDepth) {

        int currentEndIndex = endIndex;
        int currentDepth = maxDepth;

        while (currentEndIndex - startIndex > INSERTION_SORT_THRESHOLD) {
            if (currentDepth == 0) {
                heapSort(array, startIndex, currentEndIndex);
                return;
            }
            final int pivotIndex = partition(array, startIndex, currentEndIndex);
            introspectiveSort(array, pivotIndex + 1, currentEndIndex, currentDepth - 1);
            currentEndIndex = pivotIndex - 1;
            currentDepth--;
        }
        insertionSort(array, startIndex, currentEndIndex);
    }

    private static <T extends Comparable<T>> int partition(
            T[] array, final int startIndex, final int endIndex) {

        final int randomPivotIndex =
                startIndex + (int) (Math.random() * (endIndex - startIndex + 1));
        SortUtils.swap(array, randomPivotIndex, endIndex);

        final T pivotValue = array[endIndex];
        int lastSmallerElementIndex = startIndex - 1;

        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                lastSmallerElementIndex++;
                SortUtils.swap(array, lastSmallerElementIndex, currentIndex);
            }
        }
        SortUtils.swap(array, lastSmallerElementIndex + 1, endIndex);
        return lastSmallerElementIndex + 1;
    }

    private static <T extends Comparable<T>> void insertionSort(
            T[] array, final int startIndex, final int endIndex) {

        for (int currentIndex = startIndex + 1; currentIndex <= endIndex; currentIndex++) {
            final T valueToInsert = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= startIndex && array[sortedIndex].compareTo(valueToInsert) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = valueToInsert;
        }
    }

    private static <T extends Comparable<T>> void heapSort(
            T[] array, final int startIndex, final int endIndex) {

        final int heapSize = endIndex - startIndex + 1;

        for (int rootIndex = (heapSize / 2) - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, rootIndex, heapSize, startIndex);
        }

        for (int currentEndIndex = endIndex; currentEndIndex > startIndex; currentEndIndex--) {
            SortUtils.swap(array, startIndex, currentEndIndex);
            heapify(array, 0, currentEndIndex - startIndex, startIndex);
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