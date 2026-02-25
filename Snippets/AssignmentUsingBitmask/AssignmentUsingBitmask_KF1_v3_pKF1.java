package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistinctValueCombinationCounter {

    private final int maxValue;
    private final int[][] memoizedCounts;
    private final List<List<Integer>> valueToSourceLists;
    private final int allListsSelectedMask;

    public DistinctValueCombinationCounter(List<List<Integer>> lists, int maxValue) {
        this.maxValue = maxValue;
        int listCount = lists.size();

        this.memoizedCounts = new int[1 << listCount][maxValue + 1];
        for (int[] row : memoizedCounts) {
            Arrays.fill(row, -1);
        }

        this.valueToSourceLists = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToSourceLists.add(new ArrayList<>());
        }

        this.allListsSelectedMask = (1 << listCount) - 1;

        for (int listIndex = 0; listIndex < listCount; listIndex++) {
            for (int value : lists.get(listIndex)) {
                this.valueToSourceLists.get(value).add(listIndex);
            }
        }
    }

    private int countCombinations(int selectedListsMask, int currentValue) {
        if (selectedListsMask == allListsSelectedMask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (memoizedCounts[selectedListsMask][currentValue] != -1) {
            return memoizedCounts[selectedListsMask][currentValue];
        }

        int totalCombinations = countCombinations(selectedListsMask, currentValue + 1);

        for (int listIndex : valueToSourceLists.get(currentValue)) {
            if ((selectedListsMask & (1 << listIndex)) != 0) {
                continue;
            }
            int updatedSelectedListsMask = selectedListsMask | (1 << listIndex);
            totalCombinations += countCombinations(updatedSelectedListsMask, currentValue + 1);
        }

        memoizedCounts[selectedListsMask][currentValue] = totalCombinations;
        return totalCombinations;
    }

    public int countAllDistinctValueCombinations() {
        return countCombinations(0, 1);
    }
}