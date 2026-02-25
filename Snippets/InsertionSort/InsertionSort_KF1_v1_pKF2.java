package com.thealgorithms.sorts;

class Class1 implements SortAlgorithm {

    /**
     * Sorts the given array using insertion sort.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be comparable
     * @return the sorted array
     */
    @Override
    public <T extends Comparable<T>> T[] method2(T[] array) {
        return method2(array, 0, array.length);
    }

    /**
     * Sorts a subrange of the given array using insertion sort.
     *
     * @param array the array containing the subrange to be sorted
     * @param left  the starting index (inclusive) of the subrange
     * @param right the ending index (exclusive) of the subrange
     * @param <T>   the type of elements, must be comparable
     * @return the array with the specified subrange sorted
     */
    public <T extends Comparable<T>> T[] method2(T[] array, final int left, final int right) {
        if (array == null || left >= right) {
            return array;
        }

        for (int i = left + 1; i < right; i++) {
            final T key = array[i];
            int j = i - 1;
            while (j >= left && SortUtils.less(key, array[j])) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }

        return array;
    }

    /**
     * Sorts the given array using insertion sort with a preliminary step
     * that moves the minimum element to the first position.
     *
     * @param array the array to be sorted
     * @param <T>   the type of elements, must be comparable
     * @return the sorted array
     */
    public <T extends Comparable<T>> T[] method3(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = method4(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T key = array[i];
            int j = i;
            while (j > 0 && SortUtils.less(key, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = key;
        }

        return array;
    }

    /**
     * Finds the index of the minimum element in the given array.
     *
     * @param array the array to search
     * @param <T>   the type of elements, must be comparable
     * @return the index of the minimum element
     */
    private <T extends Comparable<T>> int method4(final T[] array) {
        int minIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }
        return minIndex;
    }
}