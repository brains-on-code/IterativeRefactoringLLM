package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Class1 {

    private final int maxValue;
    private final int[][] memo;
    private final List<List<Integer>> valueToIndices;
    private final int fullMask;

    public Class1(List<List<Integer>> groups, int maxValue) {
        this.maxValue = maxValue;
        int groupCount = groups.size();

        this.memo = new int[1 << groupCount][maxValue + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        this.valueToIndices = new ArrayList<>(maxValue + 1);
        for (int i = 0; i <= maxValue; i++) {
            this.valueToIndices.add(new ArrayList<>());
        }

        this.fullMask = (1 << groupCount) - 1;

        for (int groupIndex = 0; groupIndex < groupCount; groupIndex++) {
            for (int value : groups.get(groupIndex)) {
                this.valueToIndices.get(value).add(groupIndex);
            }
        }
    }

    private int countWays(int mask, int currentValue) {
        if (mask == fullMask) {
            return 1;
        }
        if (currentValue > maxValue) {
            return 0;
        }
        if (memo[mask][currentValue] != -1) {
            return memo[mask][currentValue];
        }

        int ways = countWays(mask, currentValue + 1);

        for (int groupIndex : valueToIndices.get(currentValue)) {
            if ((mask & (1 << groupIndex)) != 0) {
                continue;
            }
            ways += countWays(mask | (1 << groupIndex), currentValue + 1);
        }

        memo[mask][currentValue] = ways;
        return ways;
    }

    public int method2() {
        return countWays(0, 1);
    }
}