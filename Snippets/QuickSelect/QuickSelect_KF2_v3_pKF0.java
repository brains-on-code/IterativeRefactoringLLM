package com.thealgorithms.searches;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class QuickSelect {

    private static final int GROUP_SIZE = 5;

    private QuickSelect() {
        // Utility class; prevent instantiation
    }

    public static <T extends Comparable<T>> T select(List<T> list, int n) {
        validateInput(list, n);
        int index = selectIndex(list, n);
        return list.get(index);
    }

    private static <T extends Comparable<T>> void validateInput(List<T> list, int n) {
        Objects.requireNonNull(list, "The list of elements must not be null.");

        if (list.isEmpty()) {
            throw new IllegalArgumentException("The list of elements must not be empty.");
        }
        if (n < 0) {
            throw new IndexOutOfBoundsException("The index must not be negative.");
        }
        if (n >= list.size()) {
            throw new IndexOutOfBoundsException("The index must be less than the number of elements.");
        }
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int n) {
        return selectIndex(list, 0, list.size() - 1, n);
    }

    private static <T extends Comparable<T>> int selectIndex(List<T> list, int left, int right, int n) {
        int low = left;
        int high = right;

        while (true) {
            if (low == high) {
                return low;
            }

            int pivotIndex = choosePivotIndex(list, low, high);
            pivotIndex = partition(list, low, high, pivotIndex, n);

            if (n == pivotIndex) {
                return n;
            }
            if (n < pivotIndex) {
                high = pivotIndex - 1;
            } else {
                low = pivotIndex + 1;
            }
        }
    }

    private static <T extends Comparable<T>> int partition(
        List<T> list,
        int left,
        int right,
        int pivotIndex,
        int n
    ) {
        T pivotValue = list.get(pivotIndex);
        Collections.swap(list, pivotIndex, right);

        int storeIndex = partitionLessThanPivot(list, left, right, pivotValue);
        int storeIndexEq = partitionEqualToPivot(list, storeIndex, right, pivotValue);

        Collections.swap(list, right, storeIndexEq);

        if (n < storeIndex) {
            return storeIndex;
        }
        return Math.min(n, storeIndexEq);
    }

    private static <T extends Comparable<T>> int partitionLessThanPivot(
        List<T> list,
        int left,
        int right,
        T pivotValue
    ) {
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) < 0) {
                Collections.swap(list, storeIndex, i);
                storeIndex++;
            }
        }
        return storeIndex;
    }

    private static <T extends Comparable<T>> int partitionEqualToPivot(
        List<T> list,
        int start,
        int right,
        T pivotValue
    ) {
        int storeIndexEq = start;
        for (int i = start; i < right; i++) {
            if (list.get(i).compareTo(pivotValue) == 0) {
                Collections.swap(list, storeIndexEq, i);
                storeIndexEq++;
            }
        }
        return storeIndexEq;
    }

    private static <T extends Comparable<T>> int choosePivotIndex(List<T> list, int left, int right) {
        if (right - left < GROUP_SIZE) {
            return partition5(list, left, right);
        }

        int groupStart = left;
        for (; groupStart <= right; groupStart += GROUP_SIZE) {
            int subRight = Math.min(groupStart + GROUP_SIZE - 1, right);
            int median5 = partition5(list, groupStart, subRight);
            int targetIndex = left + (groupStart - left) / GROUP_SIZE;
            Collections.swap(list, median5, targetIndex);
        }

        int medianOfMediansLeft = left;
        int medianOfMediansRight = left + (right - left) / GROUP_SIZE;
        int medianOfMediansTarget =
            medianOfMediansLeft + (medianOfMediansRight - medianOfMediansLeft) / 2;

        return selectIndex(list, medianOfMediansLeft, medianOfMediansRight, medianOfMediansTarget);
    }

    private static <T extends Comparable<T>> int partition5(List<T> list, int left, int right) {
        List<T> subList = list.subList(left, right + 1);
        subList.sort(Comparator.naturalOrder());
        return (left + right) >>> 1;
    }
}