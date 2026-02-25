package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DistinctValueCombinationCounter {

    private final int maxValue;
    private final int[][] combinationCountCache;
    private final List<List<Integer>> valueToSourceListIndices;
    private final int allSourceListsUsedMask;

    public DistinctValueCombinationCounter(List<List<Integer>> sourceLists, int maxValue) {
        this.maxValue = maxValue;
        int sourceListCount = sourceLists.size();

        this.combinationCountCache = new int[1 << sourceListCount][maxValue + 1];
        for (int[] cacheRow : combinationCountCache) {
            Arrays.fill(cacheRow, -1);
        }

        this.valueToSourceListIndices = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToSourceListIndices.add(new ArrayList<>());
        }

        this.allSourceListsUsedMask = (1 << sourceListCount) - 1;

        for (int sourceListIndex = 0; sourceListIndex < sourceListCount; sourceListIndex++) {
            for (int value : sourceLists.get(sourceListIndex)) {
                this.valueToSourceListIndices.get(value).add(sourceListIndex);
            }
        }
    }

    private int countCombinations(int usedSourceListsMask, int currentValue) {
        if (usedSourceListsMask == allSourceListsUsedMask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (combinationCountCache[usedSourceListsMask][currentValue] != -1) {
            return combinationCountCache[usedSourceListsMask][currentValue];
        }

        int totalCombinations = countCombinations(usedSourceListsMask, currentValue + 1);

        for (int sourceListIndex : valueToSourceListIndices.get(currentValue)) {
            if ((usedSourceListsMask & (1 << sourceListIndex)) != 0) {
                continue;
            }
            int updatedUsedSourceListsMask = usedSourceListsMask | (1 << sourceListIndex);
            totalCombinations += countCombinations(updatedUsedSourceListsMask, currentValue + 1);
        }

        combinationCountCache[usedSourceListsMask][currentValue] = totalCombinations;
        return totalCombinations;
    }

    public int countAllDistinctValueCombinations() {
        return countCombinations(0, 1);
    }
}