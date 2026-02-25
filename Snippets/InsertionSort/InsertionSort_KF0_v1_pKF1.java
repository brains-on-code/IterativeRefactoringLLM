package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    /**
     * Sorts the given array using the standard Insertion Sort algorithm.
     *
     * @param array The array to be sorted
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    /**
     * Sorts a subarray of the given array using the standard Insertion Sort algorithm.
     *
     * @param array      The array to be sorted
     * @param startIndex The starting index of the subarray (inclusive)
     * @param endIndex   The ending index of the subarray (exclusive)
     * @param <T>        The type of elements in the array, which must be comparable
     * @return The sorted array
     */
    public <T extends Comparable<T>> T[] sort(T[] array, final int startIndex, final int endIndex) {
        if (array == null || startIndex >= endIndex) {
            return array;
        }

        for (int currentIndex = startIndex + 1; currentIndex < endIndex; currentIndex++) {
            final T currentValue = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= startIndex && SortUtils.less(currentValue, array[sortedIndex])) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }
            array[sortedIndex + 1] = currentValue;
        }

        return array;
    }

    /**
     * Sentinel sort is a function which on the first step finds the minimal element in the provided
     * array and puts it to the zero position, such a trick gives us an ability to avoid redundant
     * comparisons like `j > 0` and swaps (we can move elements on position right, until we find
     * the right position for the chosen element) on further step.
     *
     * @param array The array to be sorted
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The sorted array
     */
    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minElementIndex = findMinIndex(array);
        SortUtils.swap(array, 0, minElementIndex);

        for (int currentIndex = 2; currentIndex < array.length; currentIndex++) {
            final T currentValue = array[currentIndex];
            int sortedIndex = currentIndex;

            while (sortedIndex > 0 && SortUtils.less(currentValue, array[sortedIndex - 1])) {
                array[sortedIndex] = array[sortedIndex - 1];
                sortedIndex--;
            }
            array[sortedIndex] = currentValue;
        }

        return array;
    }

    /**
     * Finds the index of the minimum element in the array.
     *
     * @param array The array to be searched
     * @param <T>   The type of elements in the array, which must be comparable
     * @return The index of the minimum element
     */
    private <T extends Comparable<T>> int findMinIndex(final T[] array) {
        int minIndex = 0;
        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (SortUtils.less(array[currentIndex], array[minIndex])) {
                minIndex = currentIndex;
            }
        }
        return minIndex;
    }
}