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
     * @param array          The array to be sorted
     * @param left           The starting index of the subarray
     * @param right          The ending index of the subarray
     * @param remainingDepth The remaining recursion depth before switching to heapsort
     * @param <T>            The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void introspectiveSort(
            T[] array, final int left, int right, final int remainingDepth) {

        int currentRight = right;
        int depth = remainingDepth;

        while (currentRight - left > INSERTION_SORT_THRESHOLD) {
            if (depth == 0) {
                heapSort(array, left, currentRight);
                return;
            }
            final int pivotIndex = partition(array, left, currentRight);
            introspectiveSort(array, pivotIndex + 1, currentRight, depth - 1);
            currentRight = pivotIndex - 1;
            depth--;
        }
        insertionSort(array, left, currentRight);
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
        final T pivot = array[right];

        int lastSmallerIndex = left - 1;
        for (int index = left; index < right; index++) {
            if (array[index].compareTo(pivot) <= 0) {
                lastSmallerIndex++;
                SortUtils.swap(array, lastSmallerIndex, index);
            }
        }
        SortUtils.swap(array, lastSmallerIndex + 1, right);
        return lastSmallerIndex + 1;
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
        for (int index = left + 1; index <= right; index++) {
            final T valueToInsert = array[index];
            int sortedIndex = index - 1;

            while (sortedIndex >= left && array[sortedIndex].compareTo(valueToInsert) > 0) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = valueToInsert;
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

        for (int currentRight = right; currentRight > left; currentRight--) {
            SortUtils.swap(array, left, currentRight);
            heapify(array, 0, currentRight - left, left);
        }
    }

    /**
     * Maintains the heap property for a subarray.
     *
     * @param array       The array to be heapified
     * @param root        The index of the root element in the heap (relative to the heap)
     * @param heapSize    The size of the heap
     * @param baseOffset  The starting index of the subarray in the original array
     * @param <T>         The type of elements in the array, which must be comparable
     */
    private static <T extends Comparable<T>> void heapify(
            T[] array, final int root, final int heapSize, final int baseOffset) {

        final int leftChild = 2 * root + 1;
        final int rightChild = 2 * root + 2;
        int largest = root;

        if (leftChild < heapSize
                && array[baseOffset + leftChild].compareTo(array[baseOffset + largest]) > 0) {
            largest = leftChild;
        }
        if (rightChild < heapSize
                && array[baseOffset + rightChild].compareTo(array[baseOffset + largest]) > 0) {
            largest = rightChild;
        }
        if (largest != root) {
            SortUtils.swap(array, baseOffset + root, baseOffset + largest);
            heapify(array, largest, heapSize, baseOffset);
        }
    }
}