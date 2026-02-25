package com.thealgorithms.sorts;

/**
 * CocktailShakerSort class implements the Cocktail Shaker Sort algorithm,
 * which is a bidirectional bubble sort. It sorts the array by passing
 * through it back and forth, progressively moving the largest elements
 * to the end and the smallest elements to the beginning.
 *
 * @author Mateus Bizzo (https://github.com/MattBizzo)
 * @author Podshivalov Nikita (https://github.com/nikitap492)
 */
class CocktailShakerSort implements SortAlgorithm {

    /**
     * Sorts the given array using the Cocktail Shaker Sort algorithm.
     *
     * @param <T>   The type of elements in the array, which must be comparable
     * @param array The array to be sorted
     * @return The sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] sort(final T[] array) {
        if (array.length == 0) {
            return array;
        }

        int startIndex = 0;
        int endIndex = array.length - 1;

        while (startIndex < endIndex) {
            endIndex = forwardPass(array, startIndex, endIndex);
            startIndex = backwardPass(array, startIndex, endIndex);
        }

        return array;
    }

    /**
     * Performs a forward pass through the array, moving larger elements to the end.
     *
     * @param <T>        The type of elements in the array, which must be comparable
     * @param array      The array being sorted
     * @param startIndex The current left boundary of the sorting area
     * @param endIndex   The current right boundary of the sorting area
     * @return The index of the last swapped element during this pass
     */
    private <T extends Comparable<T>> int forwardPass(
            final T[] array,
            final int startIndex,
            final int endIndex
    ) {
        int lastSwapIndex = startIndex;

        for (int currentIndex = startIndex; currentIndex < endIndex; currentIndex++) {
            if (SortUtils.less(array[currentIndex + 1], array[currentIndex])) {
                SortUtils.swap(array, currentIndex, currentIndex + 1);
                lastSwapIndex = currentIndex;
            }
        }

        return lastSwapIndex;
    }

    /**
     * Performs a backward pass through the array, moving smaller elements to the beginning.
     *
     * @param <T>        The type of elements in the array, which must be comparable
     * @param array      The array being sorted
     * @param startIndex The current left boundary of the sorting area
     * @param endIndex   The current right boundary of the sorting area
     * @return The index of the last swapped element during this pass
     */
    private <T extends Comparable<T>> int backwardPass(
            final T[] array,
            final int startIndex,
            final int endIndex
    ) {
        int lastSwapIndex = endIndex;

        for (int currentIndex = endIndex; currentIndex > startIndex; currentIndex--) {
            if (SortUtils.less(array[currentIndex], array[currentIndex - 1])) {
                SortUtils.swap(array, currentIndex - 1, currentIndex);
                lastSwapIndex = currentIndex;
            }
        }

        return lastSwapIndex;
    }
}