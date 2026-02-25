package com.thealgorithms.sorts;

class InsertionSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] array) {
        return sort(array, 0, array.length);
    }

    public <T extends Comparable<T>> T[] sort(T[] array, final int fromIndex, final int toIndex) {
        if (array == null || fromIndex >= toIndex) {
            return array;
        }

        for (int currentIndex = fromIndex + 1; currentIndex < toIndex; currentIndex++) {
            final T elementToInsert = array[currentIndex];
            int sortedIndex = currentIndex - 1;

            while (sortedIndex >= fromIndex && SortUtils.less(elementToInsert, array[sortedIndex])) {
                array[sortedIndex + 1] = array[sortedIndex];
                sortedIndex--;
            }

            array[sortedIndex + 1] = elementToInsert;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int indexOfMinimum = findIndexOfMinimum(array);
        SortUtils.swap(array, 0, indexOfMinimum);

        for (int currentIndex = 2; currentIndex < array.length; currentIndex++) {
            final T elementToInsert = array[currentIndex];
            int sortedIndex = currentIndex;

            while (sortedIndex > 0 && SortUtils.less(elementToInsert, array[sortedIndex - 1])) {
                array[sortedIndex] = array[sortedIndex - 1];
                sortedIndex--;
            }

            array[sortedIndex] = elementToInsert;
        }

        return array;
    }

    private <T extends Comparable<T>> int findIndexOfMinimum(final T[] array) {
        int indexOfMinimum = 0;

        for (int currentIndex = 1; currentIndex < array.length; currentIndex++) {
            if (SortUtils.less(array[currentIndex], array[indexOfMinimum])) {
                indexOfMinimum = currentIndex;
            }
        }

        return indexOfMinimum;
    }
}