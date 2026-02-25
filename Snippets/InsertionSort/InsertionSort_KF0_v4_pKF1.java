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

        for (int unsortedIndex = startIndex + 1; unsortedIndex < endIndex; unsortedIndex++) {
            final T valueToInsert = array[unsortedIndex];
            int insertionIndex = unsortedIndex - 1;

            while (insertionIndex >= startIndex && SortUtils.less(valueToInsert, array[insertionIndex])) {
                array[insertionIndex + 1] = array[insertionIndex];
                insertionIndex--;
            }
            array[insertionIndex + 1] = valueToInsert;
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

        final int minElementIndex = findIndexOfMinimum(array);
        SortUtils.swap(array, 0, minElementIndex);

        for (int unsortedIndex = 2; unsortedIndex < array.length; unsortedIndex++) {
            final T valueToInsert = array[unsortedIndex];
            int insertionIndex = unsortedIndex;

            while (insertionIndex > 0 && SortUtils.less(valueToInsert, array[insertionIndex - 1])) {
                array[insertionIndex] = array[insertionIndex - 1];
                insertionIndex--;
            }
            array[insertionIndex] = valueToInsert;
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
    private <T extends Comparable<T>> int findIndexOfMinimum(final T[] array) {
        int minElementIndex = 0;
        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (SortUtils.less(array[currentIndex], array[minElementIndex])) {
                minElementIndex = currentIndex;
            }
        }
        return minElementIndex;
    }
}