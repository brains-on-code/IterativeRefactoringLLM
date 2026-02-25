package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistinctValueCombinationCounter {

    private final int maxValue;
    private final int[][] memoizedCombinationCounts;
    private final List<List<Integer>> valueToListIndices;
    private final int allListsUsedBitmask;

    public DistinctValueCombinationCounter(List<List<Integer>> lists, int maxValue) {
        this.maxValue = maxValue;
        int listCount = lists.size();

        this.memoizedCombinationCounts = new int[1 << listCount][maxValue + 1];
        for (int[] row : memoizedCombinationCounts) {
            Arrays.fill(row, -1);
        }

        this.valueToListIndices = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToListIndices.add(new ArrayList<>());
        }

        this.allListsUsedBitmask = (1 << listCount) - 1;

        for (int listIndex = 0; listIndex < listCount; listIndex++) {
            for (int value : lists.get(listIndex)) {
                this.valueToListIndices.get(value).add(listIndex);
            }
        }
    }

    private int countCombinations(int usedListsBitmask, int currentValue) {
        if (usedListsBitmask == allListsUsedBitmask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (memoizedCombinationCounts[usedListsBitmask][currentValue] != -1) {
            return memoizedCombinationCounts[usedListsBitmask][currentValue];
        }

        int totalCombinations = countCombinations(usedListsBitmask, currentValue + 1);

        for (int listIndex : valueToListIndices.get(currentValue)) {
            if ((usedListsBitmask & (1 << listIndex)) != 0) {
                continue;
            }
            int updatedUsedListsBitmask = usedListsBitmask | (1 << listIndex);
            totalCombinations += countCombinations(updatedUsedListsBitmask, currentValue + 1);
        }

        memoizedCombinationCounts[usedListsBitmask][currentValue] = totalCombinations;
        return totalCombinations;
    }

    public int countAllDistinctValueCombinations() {
        return countCombinations(0, 1);
    }
}