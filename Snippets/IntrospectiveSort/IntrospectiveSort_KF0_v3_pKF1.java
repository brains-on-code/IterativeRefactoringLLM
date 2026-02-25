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
     * @param left     The starting index of the subarray
     * @param right    The ending index of the subarray
     * @param maxDepth The remaining recursion depth before switching to heapsort
     * @param <T>      The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void introspectiveSort(
            T[] array, final int left, int right, final int maxDepth) {

        int currentRightIndex = right;

        while (currentRightIndex - left > INSERTION_SORT_THRESHOLD) {
            if (maxDepth == 0) {
                heapSort(array, left, currentRightIndex);
                return;
            }
            final int pivotIndex = partition(array, left, currentRightIndex);
            introspectiveSort(array, pivotIndex + 1, currentRightIndex, maxDepth - 1);
            currentRightIndex = pivotIndex - 1;
        }
        insertionSort(array, left, currentRightIndex);
    }

    /**
     * Partitions the array around a pivot.
     *
     * @param array The array to be partitioned
     * @param left  The starting index of the subarray
     * @param right The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The index of the pivot
     */
    private static <T extends Comparable<T>> int partition(T[] array, final int left, final int right) {
        final int pivotIndex = left + (int) (Math.random() * (right - left + 1));
        SortUtils.swap(array, pivotIndex, right);
        final T pivotValue = array[right];

        int lastSmallerElementIndex = left - 1;
        for (int currentIndex = left; currentIndex < right; currentIndex++) {
            if (array[currentIndex].compareTo(pivotValue) <= 0) {
                lastSmallerElementIndex++;
                SortUtils.swap(array, lastSmallerElementIndex, currentIndex);
            }
        }
        SortUtils.swap(array, lastSmallerElementIndex + 1, right);
        return lastSmallerElementIndex + 1;
    }

    /**
     * Sorts a subarray using insertion sort.
     *
     * @param array The array to be sorted
     * @param left  The starting index of the subarray
     * @param right The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void insertionSort(T[] array, final int left, final int right) {
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

    /**
     * Sorts a subarray using heapsort.
     *
     * @param array The array to be sorted
     * @param left  The starting index of the subarray
     * @param right The ending index of the subarray
     * @param <T>   The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapSort(T[] array, final int left, final int right) {
        final int heapSize = right - left + 1;

        for (int heapIndex = (heapSize / 2) - 1; heapIndex >= 0; heapIndex--) {
            heapify(array, heapIndex, heapSize, left);
        }

        for (int currentRightIndex = right; currentRightIndex > left; currentRightIndex--) {
            SortUtils.swap(array, left, currentRightIndex);
            heapify(array, 0, currentRightIndex - left, left);
        }
    }

    /**
     * Maintains the heap property for a subarray.
     *
     * @param array    The array to be heapified
     * @param rootIndex     The index of the root element in the heap (relative to the heap)
     * @param heapSize The size of the heap
     * @param offset   The starting index of the subarray in the original array
     * @param <T>      The type of elements in the array, which must be comparable
     */
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