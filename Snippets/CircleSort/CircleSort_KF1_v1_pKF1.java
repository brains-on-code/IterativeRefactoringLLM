package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] method1(T[] array) {
        if (array.length == 0) {
            return array;
        }
        while (method2(array, 0, array.length - 1)) {
            // keep iterating until no swaps occur
        }
        return array;
    }

    private <T extends Comparable<T>> boolean method2(final T[] array, final int left, final int right) {
        boolean swapped = false;

        if (left == right) {
            return false;
        }

        int i = left;
        int j = right;

        while (i < j) {
            if (array[i].compareTo(array[j]) > 0) {
                SortUtils.swap(array, i, j);
                swapped = true;
            }
            i++;
            j--;
        }

        if (i == j && array[i].compareTo(array[j + 1]) > 0) {
            SortUtils.swap(array, i, j + 1);
            swapped = true;
        }

        final int mid = left + (right - left) / 2;
        final boolean leftSwapped = method2(array, left, mid);
        final boolean rightSwapped = method2(array, mid + 1, right);

        return swapped || leftSwapped || rightSwapped;
    }
}