package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Class1 {

    private final int maxValue;
    private final int[][] memoizationTable;
    private final List<List<Integer>> valueToIndices;
    private final int fullMask;

    public Class1(List<List<Integer>> inputLists, int maxValue) {
        this.maxValue = maxValue;
        this.memoizationTable = new int[1 << inputLists.size()][maxValue + 1];
        for (int[] row : memoizationTable) {
            Arrays.fill(row, -1);
        }

        this.valueToIndices = new ArrayList<>(maxValue + 1);
        for (int value = 0; value <= maxValue; value++) {
            this.valueToIndices.add(new ArrayList<>());
        }

        this.fullMask = (1 << inputLists.size()) - 1;

        for (int index = 0; index < inputLists.size(); index++) {
            for (int value : inputLists.get(index)) {
                this.valueToIndices.get(value).add(index);
            }
        }
    }

    private int countCombinations(int usedMask, int currentValue) {
        if (usedMask == fullMask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (memoizationTable[usedMask][currentValue] != -1) {
            return memoizationTable[usedMask][currentValue];
        }

        int totalWays = countCombinations(usedMask, currentValue + 1);

        for (int index : valueToIndices.get(currentValue)) {
            if ((usedMask & (1 << index)) != 0) {
                continue;
            }
            totalWays += countCombinations(usedMask | (1 << index), currentValue + 1);
        }

        memoizationTable[usedMask][currentValue] = totalWays;
        return memoizationTable[usedMask][currentValue];
    }

    public int method2() {
        return countCombinations(0, 1);
    }
}