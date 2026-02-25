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

        for (int i = fromIndex + 1; i < toIndex; i++) {
            final T valueToInsert = array[i];
            int j = i - 1;

            while (j >= fromIndex && SortUtils.less(valueToInsert, array[j])) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = valueToInsert;
        }

        return array;
    }

    public <T extends Comparable<T>> T[] sentinelSort(T[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int minIndex = findIndexOfMinimum(array);
        SortUtils.swap(array, 0, minIndex);

        for (int i = 2; i < array.length; i++) {
            final T valueToInsert = array[i];
            int j = i;

            while (j > 0 && SortUtils.less(valueToInsert, array[j - 1])) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = valueToInsert;
        }

        return array;
    }

    private <T extends Comparable<T>> int findIndexOfMinimum(final T[] array) {
        int minIndex = 0;

        for (int i = 1; i < array.length; i++) {
            if (SortUtils.less(array[i], array[minIndex])) {
                minIndex = i;
            }
        }

        return minIndex;
    }
}