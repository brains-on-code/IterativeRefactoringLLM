package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistinctValueCombinationCounter {

    private final int maxValue;
    private final int[][] dpCache;
    private final List<List<Integer>> valueToListIndices;
    private final int allListsUsedMask;

    public DistinctValueCombinationCounter(List<List<Integer>> lists, int maxValue) {
        this.maxValue = maxValue;
        int listCount = lists.size();

        this.dpCache = new int[1 << listCount][maxValue + 1];
        for (int[] row : dpCache) {
            Arrays.fill(row, -1);
        }

        this.valueToListIndices = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToListIndices.add(new ArrayList<>());
        }

        this.allListsUsedMask = (1 << listCount) - 1;

        for (int listIndex = 0; listIndex < listCount; listIndex++) {
            for (int value : lists.get(listIndex)) {
                this.valueToListIndices.get(value).add(listIndex);
            }
        }
    }

    private int countCombinations(int usedListsMask, int currentValue) {
        if (usedListsMask == allListsUsedMask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (dpCache[usedListsMask][currentValue] != -1) {
            return dpCache[usedListsMask][currentValue];
        }

        int totalCombinations = countCombinations(usedListsMask, currentValue + 1);

        for (int listIndex : valueToListIndices.get(currentValue)) {
            if ((usedListsMask & (1 << listIndex)) != 0) {
                continue;
            }
            totalCombinations += countCombinations(usedListsMask | (1 << listIndex), currentValue + 1);
        }

        dpCache[usedListsMask][currentValue] = totalCombinations;
        return totalCombinations;
    }

    public int countAllDistinctValueCombinations() {
        return countCombinations(0, 1);
    }
}