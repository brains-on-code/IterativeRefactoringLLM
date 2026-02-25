package com.thealgorithms.sorts;

/**
 * Introspective Sort Algorithm Implementation
 *
 * @see <a href="https://en.wikipedia.org/wiki/Introsort">IntroSort Algorithm</a>
 */
public class IntrospectiveSort implements SortAlgorithm {

    private static final int INSERTION_SORT_THRESHOLD = 16;

    /**
     * Sorts the given array using Introspective Sort, which combines quicksort, heapsort, and insertion sort.
     *
     * @param array The array to be sorted
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        final int maxDepth = 2 * (int) (Math.log(array.length) / Math.log(2));
        introspectiveSort(array, 0, array.length - 1, maxDepth);
        return array;
    }

    /**
     * Performs introspective sort on the specified subarray.
     *
     * @param array    The array to be sorted
     * @param start    The starting index of the subarray
     * @param end      The ending index of the subarray
     * @param maxDepth The remaining recursion depth before switching to heapsort
     * @param <T>      The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void introspectiveSort(
            T[] array, final int start, int end, final int maxDepth) {

        int rightIndex = end;

        while (rightIndex - start > INSERTION_SORT_THRESHOLD) {
            if (maxDepth == 0) {
                heapSort(array, start, rightIndex);
                return;
            }
            final int pivotIndex = partition(array, start, rightIndex);
            introspectiveSort(array, pivotIndex + 1, rightIndex, maxDepth - 1);
            rightIndex = pivotIndex - 1;
        }
        insertionSort(array, start, rightIndex);
    }

    /**
     * Partitions the array around a pivot.
     *
     * @param array The array to be partitioned
     * @param start The starting index of the subarray
     * @param end   The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The index of the pivot
     */
    private static <T extends Comparable<T>> int partition(T[] array, final int start, final int end) {
        final int randomPivotIndex = start + (int) (Math.random() * (end - start + 1));
        SortUtils.swap(array, randomPivotIndex, end);
        final T pivotValue = array[end];

        int smallerElementBoundary = start - 1;
        for (int currentIndex = start; currentIndex < end; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                smallerElementBoundary++;
                SortUtils.swap(array, smallerElementBoundary, currentIndex);
            }
        }
        SortUtils.swap(array, smallerElementBoundary + 1, end);
        return smallerElementBoundary + 1;
    }

    /**
     * Sorts a subarray using insertion sort.
     *
     * @param array The array to be sorted
     * @param start The starting index of the subarray
     * @param end   The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void insertionSort(T[] array, final int start, final int end) {
        for (int currentIndex = start + 1; currentIndex <= end; currentIndex++) {
            final T currentValue = array[currentIndex];
            int previousIndex = currentIndex - 1;

            while (previousIndex >= start && array[previousIndex].compareTo(currentValue) > 0) {
                array[previousIndex + 1] = array[previousIndex];
                previousIndex--;
            }
            array[previousIndex + 1] = currentValue;
        }
    }

    /**
     * Sorts a subarray using heapsort.
     *
     * @param array The array to be sorted
     * @param start The starting index of the subarray
     * @param end   The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapSort(T[] array, final int start, final int end) {
        final int heapSize = end - start + 1;

        for (int heapIndex = (heapSize / 2) - 1; heapIndex >= 0; heapIndex--) {
            heapify(array, heapIndex, heapSize, start);
        }

        for (int currentEnd = end; currentEnd > start; currentEnd--) {
            SortUtils.swap(array, start, currentEnd);
            heapify(array, 0, currentEnd - start, start);
        }
    }

    /**
     * Maintains the heap property for a subarray.
     *
     * @param array   The array to be heapified
     * @param root    The index of the root element in the heap (relative to the heap)
     * @param heapSize The size of the heap
     * @param start   The starting index of the subarray in the original array
     * @param <T>     The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapify(
            T[] array, final int root, final int heapSize, final int start) {

        final int leftChild = 2 * root + 1;
        final int rightChild = 2 * root + 2;
        int largestIndex = root;

        if (leftChild < heapSize
                && array[start + leftChild].compareTo(array[start + largestIndex]) > 0) {
            largestIndex = leftChild;
        }
        if (rightChild < heapSize
                && array[start + rightChild].compareTo(array[start + largestIndex]) > 0) {
            largestIndex = rightChild;
        }
        if (largestIndex != root) {
            SortUtils.swap(array, start + root, start + largestIndex);
            heapify(array, largestIndex, heapSize, start);
        }
    }
}