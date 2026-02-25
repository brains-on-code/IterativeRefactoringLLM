package com.thealgorithms.sorts;

import static com.thealgorithms.maths.Ceil.ceil;
import static com.thealgorithms.maths.Floor.floor;
import static com.thealgorithms.searches.QuickSelect.select;

import java.util.Arrays;


public class WiggleSort implements SortAlgorithm {

    @Override
    public <T extends Comparable<T>> T[] sort(T[] unsorted) {
        return wiggleSort(unsorted);
    }

    private int mapIndex(int index, int n) {
        return ((2 * index + 1) % (n | 1));
    }


    private <T extends Comparable<T>> void triColorSort(T[] sortThis, T median) {
        int n = sortThis.length;
        int i = 0;
        int j = 0;
        int k = n - 1;
        while (j <= k) {
            if (0 < sortThis[mapIndex(j, n)].compareTo(median)) {
                SortUtils.swap(sortThis, mapIndex(j, n), mapIndex(i, n));
                i++;
                j++;
            } else if (0 > sortThis[mapIndex(j, n)].compareTo(median)) {
                SortUtils.swap(sortThis, mapIndex(j, n), mapIndex(k, n));
                k--;
            } else {
                j++;
            }
        }
    }

    private <T extends Comparable<T>> T[] wiggleSort(T[] sortThis) {
        T median;

        median = select(Arrays.asList(sortThis), (int) floor(sortThis.length / 2.0));

        int numMedians = 0;

        for (T sortThi : sortThis) {
            if (0 == sortThi.compareTo(median)) {
                numMedians++;
            }
        }
        if (sortThis.length % 2 == 1 && numMedians == ceil(sortThis.length / 2.0)) {
            T smallestValue = select(Arrays.asList(sortThis), 0);
            if (!(0 == smallestValue.compareTo(median))) {
                throw new IllegalArgumentException("For odd Arrays if the median appears ceil(n/2) times, "
                    + "the median has to be the smallest values in the array.");
            }
        }
        if (numMedians > ceil(sortThis.length / 2.0)) {
            throw new IllegalArgumentException("No more than half the number of values may be the same.");
        }

        triColorSort(sortThis, median);
        return sortThis;
    }
}
